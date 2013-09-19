package timezra.jruby.maven.plugin;

import java.io.FileDescriptor;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.jruby.CompatVersion;
import org.jruby.Main;
import org.jruby.Main.Status;
import org.jruby.Ruby;
import org.jruby.RubyException;
import org.jruby.RubyInstanceConfig;
import org.jruby.RubyNumeric;
import org.jruby.exceptions.RaiseException;
import org.jruby.platform.Platform;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public abstract class JRubyMojo extends AbstractMojo {

    @Parameter(property = "ruby_version")
    String ruby_version;

    private final String apiMethod;

    protected abstract String[] args() throws MojoExecutionException;

    protected abstract String getGemHome();

    public JRubyMojo(final String apiMethod) {
        this.apiMethod = apiMethod;
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        if (Platform.IS_GCJ) {
            throw new MojoExecutionException("Fatal: GCJ (GNU Compiler for Java) is not supported by JRuby.");
        }

        final String[] args = args();

        try {
            final Main main = new Main(createConfig());
            final Status status = main.run(args);
            if (status.isExit()) {
                handle(status.getStatus());
            }
        } catch (final RaiseException rj) {
            handle(rj);
        } catch (Throwable t) {
            getLog().error(ThreadContext.createRawBacktraceStringFromThrowable(t));
            while ((t = t.getCause()) != null) {
                getLog().error("Caused by:");
                getLog().error(ThreadContext.createRawBacktraceStringFromThrowable(t));
            }
            throw new MojoExecutionException("Exception caught when running " + apiMethod, t);
        }
    }

    private RubyInstanceConfig createConfig() {
        final RubyInstanceConfig config = new RubyInstanceConfig();
        final String gem_home = getGemHome();
        if (gem_home != null || ruby_version != null) {
            @SuppressWarnings("unchecked")
            final Map<Object, Object> environment = config.getEnvironment();
            final Hashtable<Object, Object> newEnvironment = new Hashtable<>(environment);
            if (gem_home != null) {
                newEnvironment.put("GEM_HOME", gem_home);
            }
            if (ruby_version != null) {
                config.setCompatVersion(CompatVersion.getVersionFromString(ruby_version));
                newEnvironment.put("RUBY_VERSION", ruby_version);
            }
            config.setEnvironment(Collections.unmodifiableMap(newEnvironment));
        }
        return config;
    }

    private void handle(final RaiseException re) throws MojoExecutionException {
        final RubyException raisedException = re.getException();
        final Ruby runtime = raisedException.getRuntime();
        if (runtime.getSystemExit().isInstance(raisedException)) {
            final IRubyObject status = raisedException.callMethod(runtime.getCurrentContext(), "status");
            if (status != null && !status.isNil()) {
                handle(RubyNumeric.fix2int(status));
            } else {
                return;
            }
        } else {
            getLog().error(
                    runtime.getInstanceConfig().getTraceType()
                            .printBacktrace(raisedException, runtime.getPosix().isatty(FileDescriptor.err)));
            handle(1);
        }
    }

    private void handle(final int status) throws MojoExecutionException {
        if (status != 0) {
            throw new MojoExecutionException("Exiting with status " + status);
        }
    }
}
