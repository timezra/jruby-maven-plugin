package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Parameter;

public abstract class RequiresGemHome extends JRubyMojo {

    @Parameter(required = true, property = "gem_home")
    private String gem_home;

    public RequiresGemHome(final String apiMethod) {
        super(apiMethod);
    }

    @Override
    protected final String getGemHome() {
        return gem_home;
    }
}
