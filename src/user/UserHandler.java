package user;

import db.UserDBHandler;
import org.codehaus.jackson.JsonNode;

public class UserHandler {
    UserDBHandler db = new UserDBHandler();

    public String getUserData(String username) {
        return db.selectUserData(username);
    }

    public String createUser(JsonNode credentials) {
        return db.insertUser(credentials);
    }

    public String loginUser(JsonNode credentials) {
        return db.checkLoginCredentials(credentials);
    }

    public int getUserCoins(String username) {
        return db.selectUserCoins(username);
    }

    public void updateCoins(int newCoinValue, String username) {
        db.updateUserCoins(newCoinValue, username);
    }

    public String updateProfile(JsonNode credentials, String username) {
        db.updateUserProfile(credentials, username);
        return "profile updated";
    }

    public String getStats(String username) {
        return db.selectUserStats(username);
    }

    public String getScoreboard() {
        return db.selectScores();
    }

}
