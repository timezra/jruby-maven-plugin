package timezra.jruby.maven.plugin;

import java.io.FileDescriptor;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
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

    private static final String RUBY_VERSION = "jruby.compat.version";
    private static final String GEM_HOME = "gem.home";

    @Parameter(property = "gem_home")
    String gem_home;

    @Parameter(property = "ruby_version")
    String ruby_version;

    private final String apiMethod;

    protected abstract String[] args() throws MojoExecutionException;

    public JRubyMojo(final String apiMethod) {
        this.apiMethod = apiMethod;
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        if (Platform.IS_GCJ) {
            throw new MojoExecutionException("Fatal: GCJ (GNU Compiler for Java) is not supported by JRuby.");
        }

        final String[] args = args();

        final String originalGemHome = replaceProperty(GEM_HOME, gem_home);
        final String originalRubyVersion = replaceProperty(RUBY_VERSION, ruby_version);

        try {
            final Main main = new Main(new RubyInstanceConfig());
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
        } finally {
            setOrClearProperty(GEM_HOME, originalGemHome);
            setOrClearProperty(RUBY_VERSION, originalRubyVersion);
        }
    }

    private String replaceProperty(final String key, final String value) {
        final String originalValue = System.getProperty(key);
        if (value != null) {
            System.setProperty(key, value);
        }
        return originalValue;
    }

    private void setOrClearProperty(final String key, final String value) {
        if (value == null) {
            System.clearProperty(key);
        } else {
            System.setProperty(key, value);
        }
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
