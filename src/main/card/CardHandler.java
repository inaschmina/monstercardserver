package card;

import db.CardDBHandler;
import org.codehaus.jackson.JsonNode;

import java.util.Objects;

public class CardHandler {
    CardDBHandler db = new CardDBHandler();



    public String createCard(JsonNode credentials, int packageID, String user, boolean deck) {
        String name = credentials.get("Name").toString();
        String element;
        if(name.toLowerCase().contains("water")) {
            element = "water";
        }
        else if (name.toLowerCase().contains("fire")) {
            element = "fire";
        }
        else element = "regular";

        return db.insertCard(credentials, element, packageID, user, deck);
    }

    public String getAllCardsFromUser(String username) {
        return "{\"code\": \"200\", \"message\": \"{"+ db.selectAllCardsFromUser(username) +"}\"}";
    }

    public String createDeck(JsonNode credentials, String username) {
        String returnString = "{\"code\": \"200\", \"message\": \"creating deck failed\"}";
        for( int i = 0; i < 4; i++) {
            if (credentials.size() != 4) return "{\"code\": \"400\", \"message\": \"deck must consist of four cards\"}";
            returnString = db.insertDeck(credentials.get(i).getTextValue(), username);
        }
        return returnString;
    }

    public String getDeck(String username, Boolean plain) {
        String returnString = db.selectDeck(username, plain);
        if(Objects.equals(returnString, "")) return "{\"code\": \"400\", \"message\": \"no deck configured\"}";
        else return returnString;
    }

    public void updateOwnership(String username, int id) {
        db.updatePackageOwner(username, id);
    }

    public String getOwnerFromCard(String id) {
        return db.selectOwnerToId(id);
    }

    public String updateOwner(String id, String username) {
        return db.updateOwnerToId(id, username);
    }

    public void lockOrUnlockCard(String id, boolean lock) {
        db.UnLockCard(id, lock);
    }
}
