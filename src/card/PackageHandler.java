package card;

import db.CardDBHandler;
import db.PackageDBHandler;
import db.UserDBHandler;
import org.codehaus.jackson.JsonNode;

public class PackageHandler {

    public String createPackage(JsonNode credentials) {
        CardHandler cardHandler = new CardHandler();
        PackageDBHandler db = new PackageDBHandler();
        int packageID = db.insertPackage(5);
        for(int i=0; i < 5; i++) {
            cardHandler.createCard(credentials.get(i), packageID, "null", false);
        }
        return "true";
    }



    public String aquirePackgae(JsonNode credentials, String username, int cost) {
        CardDBHandler cardDB = new CardDBHandler();
        UserDBHandler userDB = new UserDBHandler();
        PackageDBHandler packageDB = new PackageDBHandler();
        int coins = userDB.getUserCoins(username);
        if (coins < cost) return "not enough money";
        int id = packageDB.selectPackage();
        if(id == 0) return "no packages available";
        cardDB.updateOwnership(username, id);
        userDB.updateUserCoins(coins-cost, username);
        packageDB.deletePackage(id);
        return "package " + id + " bought by " + username;
    }
}
