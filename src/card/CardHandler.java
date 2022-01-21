package card;

import db.CardDBHandler;
import org.codehaus.jackson.JsonNode;

import java.util.Locale;

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
}
