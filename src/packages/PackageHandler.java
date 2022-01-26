package packages;

import card.CardHandler;
import db.CardDBHandler;
import db.PackageDBHandler;
import db.UserDBHandler;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import user.UserHandler;

import java.io.IOException;

public class PackageHandler {

    public String createPackage(JsonNode credentials) throws IOException {
        CardHandler cardHandler = new CardHandler();
        PackageDBHandler db = new PackageDBHandler();
        String returnValue = "false";
        int packageID = db.insertPackage(5);
        for(int i=0; i < 5; i++) {
            returnValue = cardHandler.createCard(credentials.get(i), packageID, "null", false);
        }
        return returnValue;
    }


    public String aquirePackgae(JsonNode credentials, String username, int cost) {
        CardHandler cardHandler = new CardHandler();
        UserHandler userHandler = new UserHandler();
        PackageDBHandler packageDB = new PackageDBHandler();
        int coins = userHandler.getUserCoins(username);
        if (coins < cost) return "{\"code\": \"400\", \"message\": \"not enough money\"}";
        int id = packageDB.selectPackage();
        if(id == 0) return "{\"code\": \"400\", \"message\": \"no packages available\"}";
        cardHandler.updateOwnership(username, id);
        userHandler.updateCoins(coins-cost, username);
        packageDB.deletePackage(id);
        return "{\"code\": \"200\", \"message\": \"package " + id + " bought by " + username +" \"}";
    }
}
