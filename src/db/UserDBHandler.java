package db;

import org.codehaus.jackson.JsonNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBHandler extends DBconnection{

    public String insertUser(JsonNode credentials) {
        int returnValue = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO users (username, password)
            VALUES (?,?)
            """);
            preparedStatement.setString(1, credentials.get("Username").getTextValue());
            preparedStatement.setString(2, credentials.get("Password").getTextValue());
            returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(returnValue == 0) return "false";
        else return "true";
    }

    public String checkLoginCredentials(JsonNode credentials) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT username,password FROM users
            WHERE username=? AND password=?
            """);
            preparedStatement.setString(1, credentials.get("Username").getTextValue());
            preparedStatement.setString(2, credentials.get("Password").getTextValue());
            ResultSet result = preparedStatement.executeQuery();
            String resultString = Boolean.toString(result.next());
            preparedStatement.close();
            connection.close();
            return resultString;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "action failed";
    }

    public String selectUserData(String username) {
        StringBuilder resultString = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT * FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) resultString.append("Username: ").append(result.getString(1))
                    .append(" Coins: ").append(result.getInt(3)).append(" Bio: ").append(result.getString(4))
                    .append(" Image: ").append(result.getString(5)).append(" Name: ").append(result.getString(6));
            String s = resultString.toString();
            preparedStatement.close();
            connection.close();
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "user not found";
    }

    public int selectUserCoins(String username) {
        int coins = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT coins FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) coins = result.getInt(1);
            //preparedStatement.close();
            //connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return coins;
    }

    public String selectUserStats(String username) {
        StringBuilder returnString = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT won, lost, elo FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            String s = "";
            if(result.next()) s = returnString.append("User: ").append(username).append(" Won: ").append(result.getInt(1)).append(" Lost: ")
                    .append(result.getInt(2)).append(" ELO: ").append(result.getInt(3)).toString();
            preparedStatement.close();
            connection.close();
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "user not found";
    }

    public void updateUserCoins(int newCoinValue, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE users SET coins = ?
            WHERE username=?
            """);
            preparedStatement.setInt(1, newCoinValue);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
            //preparedStatement.close();
            //connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void updateUserProfile(JsonNode credentials, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE users SET "bio" = ?, "image" = ?, "name" = ?
            WHERE username=?
            """);
            preparedStatement.setString(1, credentials.get("Bio").getTextValue());
            preparedStatement.setString(2, credentials.get("Image").getTextValue());
            preparedStatement.setString(3, credentials.get("Name").getTextValue());
            preparedStatement.setString(4, username);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String selectScores() {
        StringBuilder returnString = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT username, elo FROM users
            """);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) returnString.append("User: ").append(result.getString(1)).append(" ELO: ")
                    .append(result.getInt(2)).append("\r\n");
            String s = returnString.toString();
            preparedStatement.close();
            connection.close();
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "error in scoreboard";
    }

}
