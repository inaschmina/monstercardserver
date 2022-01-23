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
            //preparedStatement.close();
            //connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "false";
        }
        return "true";
    }


    public void updatePackageOwner(String username, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE cards SET "user" = ?
            WHERE package_id = ?
            """);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String selectAllCardsFromUser(String username) {
        StringBuilder resultString = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT * FROM "cards"
            WHERE "user" = ?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                resultString.append("ID: ").append(result.getString(1)).append(" Name: ")
                        .append(result.getString(2)).append(" Damage: ").append(result.getDouble(3))
                        .append(" Element: ").append(result.getString(4)).append(" Package ID: ")
                        .append(result.getInt(5)).append(" Deck: ").append(result.getBoolean(7))
                        .append("\r\n");
            }
            String returnString = resultString.toString();
            preparedStatement.close();
            connection.close();
            return returnString;
    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultString.toString();
    }


    public String insertDeck(String id, String username) throws SQLException {
        try {
        PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE cards SET "deck" = ?
            WHERE "id" = ? AND "user" = ?
            """);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, username);
            int resultInt = preparedStatement.executeUpdate();
            //preparedStatement.close();
            //connection.close();
            if(resultInt > 0) return "deck created";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "deck not created";
    }

    public String selectDeck(String username, Boolean plain) {
        String s = "";
        StringBuilder resultString = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT * FROM cards
            WHERE "deck" = ? AND "user" = ?
            """);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, username);
            ResultSet result = preparedStatement.executeQuery();
            if(plain) {
                while (result.next()) {
                    resultString.append(result.getString(1)).append(",")
                            .append(result.getString(2)).append(",").append(result.getDouble(3))
                            .append(",").append(result.getString(4)).append(",")
                            .append(result.getInt(5)).append("\r\n");
                }
            }
            else {
                while(result.next()) {
                    resultString.append("ID: ").append(result.getString(1)).append(" Name: ")
                            .append(result.getString(2)).append(" Damage: ").append(result.getDouble(3))
                            .append(" Element: ").append(result.getString(4)).append(" Package ID: ")
                            .append(result.getInt(5)).append("\r\n");
                }
            }
            s = resultString.toString();
            preparedStatement.close();
            connection.close();
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "deck not found";
    }

}
