package directive;

import card.PackageHandler;
import db.UserDBHandler;
import org.codehaus.jackson.JsonNode;

import java.util.Objects;


public class MethodManager_Impl implements DirectiveManager {
    UserDBHandler userDB = new UserDBHandler();
    PackageHandler packageHandler = new PackageHandler();
    String returnValue;

    @Override
    public String handleDirective(String directive, String path, JsonNode body, String username) {
        switch (directive.toUpperCase()) {
            case "POST" -> this.returnValue = postRequest(path, body, username);
            case "GET" -> this.returnValue = getRequest(path, body);
            case "DELETE" -> this.returnValue = deleteRequest(path, body);
            case "PUT" -> this.returnValue = putRequest(path, body);
        }
        return this.returnValue;
    }

    private String postRequest(String path, JsonNode body, String username) {
        switch (path) {
            case "/users" -> this.returnValue = userDB.createUser(body);
            case "/sessions"-> this.returnValue = userDB.loginUser(body);
            case "/packages" -> {
                if(Objects.equals(username, "admin"))this.returnValue = packageHandler.createPackage(body);
                else return "false";
            }
            case "/transactions/packages"-> this.returnValue = packageHandler.aquirePackgae(body, username, 5);
            case "/tradings"-> this.returnValue = "";
            case "/battles"-> this.returnValue = "";
        }
        return this.returnValue;
    }

    private String getRequest(String path, JsonNode body) {
        switch (path) {
            case "/cards"-> this.returnValue = "";
            case "/deck"-> this.returnValue = "";
            case "/deck?format=plain"-> this.returnValue = "";
            case "/users"-> this.returnValue = "";
            case "/stats"-> this.returnValue = "";
            case "/score"-> this.returnValue = "";
            case "/tradings"-> this.returnValue = "";
        }
        return this.returnValue;
    }

    private String deleteRequest(String path, JsonNode body) {
        switch (path) {
            case "/tradings"-> this.returnValue = "";
        }
        return this.returnValue;
    }

    private String putRequest(String path, JsonNode body) {
        switch (path) {
            case "/deck"-> this.returnValue = "";
            case "/users"-> this.returnValue = "";
        }
        return this.returnValue;
    }
}
