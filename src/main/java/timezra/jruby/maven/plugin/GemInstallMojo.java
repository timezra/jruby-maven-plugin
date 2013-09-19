package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = GemInstallMojo.API_METHOD)
public class GemInstallMojo extends RequiresGemHome {

    static final String API_METHOD = "gem-install";

    @Parameter(required = true, property = "gem")
    String gem;

    public GemInstallMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() {
        return new String[] { "-S", "gem", "install", gem, "-i", getGemHome(), "--no-ri", "--no-rdoc" };
    }
}
