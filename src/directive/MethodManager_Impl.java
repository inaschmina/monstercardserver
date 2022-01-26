package directive;

import battle.Battle;
import card.CardHandler;
import packages.PackageHandler;
import org.codehaus.jackson.JsonNode;
import tradings.TradingsHandler;
import user.UserHandler;

import java.io.IOException;
import java.util.Objects;


public class MethodManager_Impl implements DirectiveManager {
    UserHandler userHandler = new UserHandler();
    PackageHandler packageHandler = new PackageHandler();
    CardHandler cardHandler = new CardHandler();
    TradingsHandler tradingHandler = new TradingsHandler();
    Battle battle = new Battle();
    String returnValue;

    @Override
    public String handleDirective(String directive, String path, JsonNode body, String username) {
        if(path.contains("users/")){
            String userPath = path.split("/users/")[1];
            if(Objects.equals(userPath, username)) path = "/users";
            else return "{\"code\": \"401\", \"message\": \"user is not authorized for this action\"}";
        }
        switch (directive.toUpperCase()) {
            case "POST" -> this.returnValue = postRequest(path, body, username);
            case "GET" -> this.returnValue = getRequest(path, username);
            case "DELETE" -> this.returnValue = deleteRequest(path);
            case "PUT" -> this.returnValue = putRequest(path, body, username);
        }
        return this.returnValue;
    }

    private String postRequest(String path, JsonNode body, String username) {
        String tradingId = "";
        if(path.contains("tradings/")){
            tradingId = path.split("/tradings/")[1];
            path = "/tradings/";
        }
        switch (path) {
            case "/users" -> this.returnValue = userHandler.createUser(body);
            case "/sessions"-> this.returnValue = userHandler.loginUser(body);
            case "/packages" -> {
                if(Objects.equals(username, "admin")) {
                    try {
                        this.returnValue = packageHandler.createPackage(body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else return "{\"code\": \"401\", \"message\": \"only admin is authorized to create packages\"}";
            }
            case "/transactions/packages"-> this.returnValue = packageHandler.aquirePackgae(body, username, 5);
            case "/tradings"-> this.returnValue = tradingHandler.createTradingDeal(body);
            case "/tradings/" -> this.returnValue = tradingHandler.trade(body, tradingId, username);
            case "/battles"-> this.returnValue = battle.battle(username);
        }
        return this.returnValue;
    }

    private String getRequest(String path, String username) {
        switch (path) {
            case "/cards" -> {
                if(!Objects.equals(username, "")) this.returnValue = cardHandler.getAllCardsFromUser(username);
                else this.returnValue = "{\"code\": \"401\", \"message\": \"user is not authorized for this action\"}";
            }
            case "/deck" -> this.returnValue = cardHandler.getDeck(username, false);
            case "/deck?format=plain" -> this.returnValue = cardHandler.getDeck(username, true);
            case "/users"-> this.returnValue = userHandler.getUserData(username);
            case "/stats"-> this.returnValue = userHandler.getStats(username);
            case "/score"-> this.returnValue = userHandler.getScoreboard();
            case "/tradings"-> this.returnValue = tradingHandler.checkTradingDeals();
        }
        return this.returnValue;
    }

    private String deleteRequest(String path) {
        String tradingId = "";
        if(path.contains("tradings/")){
            tradingId = path.split("/tradings/")[1];
            path = "/tradings";
        }
        switch (path) {
            case "/tradings"-> this.returnValue = tradingHandler.deleteTradeOffer(tradingId);
        }
        return this.returnValue;
    }

    private String putRequest(String path, JsonNode body, String username) {
        switch (path) {
            case "/deck" -> {
                if(body != null) this.returnValue = cardHandler.createDeck(body, username);
                else return "{\"code\": \"400\", \"message\": \"deck is not configured\"}";
            }
            case "/users"-> this.returnValue = userHandler.updateProfile(body, username);
        }
        return this.returnValue;
    }
}
