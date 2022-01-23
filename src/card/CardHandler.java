package card;

import db.CardDBHandler;
import org.codehaus.jackson.JsonNode;

import java.sql.SQLException;
import java.util.Locale;
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
        return db.selectAllCardsFromUser(username);
    }

    public String createDeck(JsonNode credentials, String username) {
        String returnString = "false";
        for( int i = 0; i < 4; i++) {
            try {
                if (credentials.get(i) == null) return "not enough crads to configure deck";
                returnString = db.insertDeck(credentials.get(i).getTextValue(), username);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return returnString;
    }

    public String getDeck(String username, Boolean plain) {
        String returnString = db.selectDeck(username, plain);
        if(Objects.equals(returnString, "")) return "no deck configured";
        else return returnString;
    }

    public void updateOwnership(String username, int id) {
        db.updatePackageOwner(username, id);
    }
}
