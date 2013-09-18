package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = GemInstallMojo.API_METHOD)
public class GemInstallMojo extends JRubyMojo {

    static final String API_METHOD = "gem-install";

    @Parameter(required = true, property = "gem_home")
    String gem_home;

    @Parameter(required = true, property = "gem")
    String gem;

    public GemInstallMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() {
        return new String[] { "-S", "gem", "install", gem, "-i", gem_home, "--no-ri", "--no-rdoc" };
    }
}
