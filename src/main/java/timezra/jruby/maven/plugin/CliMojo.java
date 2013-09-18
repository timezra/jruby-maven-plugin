package timezra.jruby.maven.plugin;

import static org.codehaus.plexus.util.cli.CommandLineUtils.translateCommandline;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = CliMojo.API_METHOD)
public class CliMojo extends JRubyMojo {

    static final String API_METHOD = "cli";

    @Parameter(required = true, property = "args")
    String args;

    public CliMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() throws MojoExecutionException {
        try {
            return translateCommandline(args);
        } catch (final Exception e) {
            throw new MojoExecutionException("Unable to parse the cli arguments", e);
        }
    }
}
