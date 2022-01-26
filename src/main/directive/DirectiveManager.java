package directive;

import org.codehaus.jackson.JsonNode;

public interface DirectiveManager {
    public String handleDirective(String directive, String path, JsonNode body, String username);
}
