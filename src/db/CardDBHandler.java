package db;

import org.codehaus.jackson.JsonNode;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardDBHandler extends DBconnection {

    public String createCard(JsonNode credentials, String element, int packageID, String user, boolean deck) {
        int returnValue = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO cards (id, name, damage, element, package_id, user, deck)
            VALUES (?,?,?,?,?,?,?)
            """);
            preparedStatement.setString(1, credentials.get("Id").getTextValue());
            preparedStatement.setString(2, credentials.get("Name").getTextValue());
            preparedStatement.setString(3, credentials.get("Damage").getTextValue());
            preparedStatement.setString(4, element);
            preparedStatement.setInt(5, packageID);
            preparedStatement.setString(6, user);
            preparedStatement.setBoolean(7, deck);
            returnValue = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(returnValue == 0) return "false";
        else return "true";
    }

}
