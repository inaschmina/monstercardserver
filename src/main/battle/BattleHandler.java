package battle;

import card.Card;
import db.BattleDBHandler;

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
        int oldWon = db.selectUserWon(username);
        int oldLost = db.selectUserLost(username);
        int newWon = oldWon + won;
        int newLost = oldLost + lost;
        int ratio = (newWon+newLost)/2;
        db.updateUserStats(username, newWon, newLost, elo, ratio);
    }

    public String ckeckOpponent() {
        return db.getOpponent();
    }

}
