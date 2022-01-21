package card;

import db.CardDBHandler;
import org.codehaus.jackson.JsonNode;

public class PackageHandler {

    public String createPackage(JsonNode credentials) {
        CardHandler cardHandler = new CardHandler();
        CardDBHandler db = new CardDBHandler();
        int packageID = db.insertPackage();
        for(int i=0; i < 5; i++) {
            cardHandler.createCard(credentials.get(i), packageID, "null", false);
        }
        return "true";
    }
}
