package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = BundleInitMojo.API_METHOD)
public class BundleInitMojo extends RequiresGemHome {

    static final String API_METHOD = "bundle-init";

    @Parameter(property = "gemspec")
    String gemspec;

    public BundleInitMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() {
        // @formatter:off
        return gemspec == null ? 
                new String[] { "-S", getGemHome() + "/bin/bundle", "init" } : 
                new String[] { "-S", getGemHome() + "/bin/bundle", "init", "--gemspec=" + gemspec };
        // @formatter:on
    }
}
