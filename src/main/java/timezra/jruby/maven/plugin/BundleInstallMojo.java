package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = BundleInstallMojo.API_METHOD)
public class BundleInstallMojo extends RequiresGemHome {

    static final String API_METHOD = "bundle-install";

    @Parameter(required = true, property = "gemfile")
    String gemfile;

    public BundleInstallMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() {
        return new String[] { "-S", getGemHome() + "/bin/bundle", "install", "--gemfile=" + gemfile };
    }
}
