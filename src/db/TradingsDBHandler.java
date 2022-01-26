package db;

import org.codehaus.jackson.JsonNode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingsDBHandler {

    public String selectAllTradings() {
        StringBuilder resultString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT * FROM tradings
            """);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                resultString.append("{\"id\":\"").append(result.getString(1))
                        .append("\", \"card_to_trade\":\"").append(result.getString(2))
                        .append("\", \"type\":\"").append(result.getString(3))
                        .append("\", \"min_damage\":\"").append(result.getInt(4))
                        .append("\"}\r\n");
            }
            String returnString = resultString.toString();
            preparedStatement.close();
            conn.close();
            if(!returnString.equals("")) return "{\"code\": \"200\", \"message\": \"" + resultString +" \"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"404\", \"message\": \"select of tradings failed\"}";
    }

    public String insertTrade(JsonNode credentials) {
        int returnValue = 0;
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            INSERT INTO tradings (id, card_to_trade, type, min_damage)
            VALUES (?,?,?,?)
            """);
            preparedStatement.setString(1, credentials.get("Id").getTextValue());
            preparedStatement.setString(2, credentials.get("CardToTrade").getTextValue());
            preparedStatement.setString(3, credentials.get("Type").getTextValue());
            preparedStatement.setInt(4, credentials.get("MinimumDamage").asInt());
            returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(returnValue == 0) return "{\"code\": \"400\", \"message\": \"failed to create trade\"}";
        else return "{\"code\": \"200\", \"message\": \"trade created\"}";
    }

    public String deleteTrade(String id) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            DELETE FROM tradings
            WHERE "id" = ?
            """);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
            return "{\"code\": \"200\", \"message\": \"trading deleted\"}";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "{\"code\": \"400\", \"message\": \"delete of trading failed\"}";
    }


    public String selectCardToTradeId(String id) {
        StringBuilder resultString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT "card_to_trade" FROM "tradings"
            WHERE "id" = ?
            """);
            preparedStatement.setString(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) resultString.append(result.getString(1));
            String returnString = resultString.toString();
            preparedStatement.close();
            conn.close();
            return returnString;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "card not found";
    }


}
