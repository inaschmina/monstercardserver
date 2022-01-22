package db;
import org.codehaus.jackson.JsonNode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDBHandler extends DBconnection {

    public String insertCard(JsonNode credentials, String element, int packageID, String user, boolean deck) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO cards ("id", "name", "damage", "element", "package_id", "user", "deck")
            VALUES (?,?,?,?,?,?,?)
            """);
            preparedStatement.setString(1, credentials.get("Id").getTextValue());
            preparedStatement.setString(2, credentials.get("Name").getTextValue());
            preparedStatement.setDouble(3, Double.parseDouble(credentials.get("Damage").getValueAsText()));
            preparedStatement.setString(4, element);
            preparedStatement.setInt(5, packageID);
            preparedStatement.setString(6, user);
            preparedStatement.setBoolean(7, deck);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "false";
        }
        return "true";
    }


    public void updateOwnership(String username, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE cards SET "user" = ?
            WHERE package_id = ?
            """);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
