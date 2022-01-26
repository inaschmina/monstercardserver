package db;

import org.codehaus.jackson.JsonNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBHandler {

    public String insertUser(JsonNode credentials) {
        int returnValue = 0;
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            INSERT INTO users (username, password)
            VALUES (?,?)
            """);
            preparedStatement.setString(1, credentials.get("Username").getTextValue());
            preparedStatement.setString(2, credentials.get("Password").getTextValue());
            returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(returnValue == 0) return "{\"code\": \"400\", \"message\": \"creating user failed\"}";
        else return "{\"code\": \"200\", \"message\": \"user created\"}";
    }

    public String checkLoginCredentials(JsonNode credentials) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT username,password FROM users
            WHERE username=? AND password=?
            """);
            preparedStatement.setString(1, credentials.get("Username").getTextValue());
            preparedStatement.setString(2, credentials.get("Password").getTextValue());
            ResultSet result = preparedStatement.executeQuery();
            String resultString = Boolean.toString(result.next());
            preparedStatement.close();
            conn.close();
            if (resultString.equals("true")) return "{\"code\": \"200\", \"message\": \"" + resultString +" \"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"400\", \"message\": \"select of user data failed\"}";
    }

    public String selectUserData(String username) {
        StringBuilder resultString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT * FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) resultString.append("{\"username\":\"").append(result.getString(1))
                    .append("\", \"coins\":\"").append(result.getInt(3))
                    .append("\", \"bio\":\"").append(result.getString(4))
                    .append("\", \"image\":\"").append(result.getString(5))
                    .append("\", \"name\":\"").append(result.getString(6)).append("\"}");
            String s = resultString.toString();
            preparedStatement.close();
            conn.close();
            return "{\"code\": \"200\", \"message\": \"" + s +" \"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"404\", \"message\": \"user not found\"}";
    }

    public int selectUserCoins(String username) {
        int coins = 0;
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT coins FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) coins = result.getInt(1);
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return coins;
    }

    public String selectUserStats(String username) {
        StringBuilder returnString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT won, lost, elo FROM users
            WHERE username=?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            String s = "";
            if(result.next()) s = returnString.append("{\"user\":\"").append(username)
                    .append("\", \"won\":\"").append(result.getInt(1))
                    .append("\", \"lost\":\"").append(result.getInt(2))
                    .append("\", \"elo\":\"").append(result.getInt(3)).append("\"}").toString();
            preparedStatement.close();
            conn.close();
            return "{\"code\": \"200\", \"message\": \"" + s +" \"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"404\", \"message\": \"user not found\"}";
    }

    public void updateUserCoins(int newCoinValue, String username) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE users SET coins = ?
            WHERE username=?
            """);
            preparedStatement.setInt(1, newCoinValue);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void updateUserProfile(JsonNode credentials, String username) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE users SET "bio" = ?, "image" = ?, "name" = ?
            WHERE username=?
            """);
            preparedStatement.setString(1, credentials.get("Bio").getTextValue());
            preparedStatement.setString(2, credentials.get("Image").getTextValue());
            preparedStatement.setString(3, credentials.get("Name").getTextValue());
            preparedStatement.setString(4, username);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String selectScores() {
        StringBuilder returnString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT username, elo FROM users
            """);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) returnString.append("{\"user\":\"").append(result.getString(1))
                    .append("\", \"elo\":\"").append(result.getInt(2)).append("\"}\r\n");
            String s = returnString.toString();
            preparedStatement.close();
            conn.close();
            return "{\"code\": \"200\", \"message\": \"" + s +" \"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"400\", \"message\": \"cant get scoreboard\"}";
    }

    public int selectELO(String username) {
        int returnValue = -1;
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT elo FROM users
            WHERE username = ?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) returnValue = result.getInt(1);
            preparedStatement.close();
            conn.close();
            return returnValue;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return returnValue;
    }

}
