package battle;

import card.Card;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.BattleDBHandler;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

public class BattleHandler {
    BattleDBHandler db = new BattleDBHandler();

    public void updateGameStatus(String username, boolean ready) {
        db.updateReadyForBattle(username, ready);
    }

    public ArrayList<Card> getDeckArray(String username) {
        return db.getDeckAsCardObjects(username);
    }

    public void updateStats(String username, int won, int lost, int elo) {
        db.updateUserStats(username, won, lost, elo);
    }

    public String ckeckOpponent() {
        return db.getOpponent();
    }

}
