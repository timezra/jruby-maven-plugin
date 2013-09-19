package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Parameter;

public abstract class OptionalGemHome extends JRubyMojo {

    @Parameter(property = "gem_home")
    private String gem_home;

    public OptionalGemHome(final String apiMethod) {
        super(apiMethod);
    }

    @Override
    protected final String getGemHome() {
        return gem_home;
    }
}
