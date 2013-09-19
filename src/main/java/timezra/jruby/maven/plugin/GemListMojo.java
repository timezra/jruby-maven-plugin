package timezra.jruby.maven.plugin;

import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = GemListMojo.API_METHOD)
public class GemListMojo extends OptionalGemHome {

    static final String API_METHOD = "gem-list";

    public GemListMojo() {
        super(API_METHOD);
    }

    @Override
    protected String[] args() {
        return new String[] { "-S", "gem", "list" };
    }
}
