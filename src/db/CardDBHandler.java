package db;
import org.codehaus.jackson.JsonNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDBHandler {

    public String insertCard(JsonNode credentials, String element, int packageID, String user, boolean deck) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
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
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "false";
        }
        return "true";
    }


    public void updatePackageOwner(String username, int id) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE cards SET "user" = ?
            WHERE package_id = ?
            """);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String selectAllCardsFromUser(String username) {
        StringBuilder resultString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT * FROM "cards"
            WHERE "user" = ?
            """);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                resultString.append("{\"id\":\"").append(result.getString(1))
                        .append("\", \"name\":\"").append(result.getString(2))
                        .append("\", \"damage\":\"").append(result.getDouble(3))
                        .append("\", \"element\":\"").append(result.getString(4))
                        .append("\", \"package_id\":\"").append(result.getInt(5))
                        .append("\", \"deck\":\"").append(result.getBoolean(7))
                        .append("\"}\r\n");
            }
            String returnString = resultString.toString();
            preparedStatement.close();
            conn.close();
            return returnString;
    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultString.toString();
    }


    public String insertDeck(String id, String username){
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE cards SET "deck" = ?
            WHERE "id" = ? AND "user" = ?
            """);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, username);
            int resultInt = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
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
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
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
                    resultString.append("{\"id\":\"").append(result.getString(1))
                            .append("\", \"name\":\"").append(result.getString(2))
                            .append("\", \"damage\":\"").append(result.getDouble(3))
                            .append("\", \"element\":\"").append(result.getString(4))
                            .append("\", \"package_id\":\"").append(result.getInt(5)).append("\"}\r\n");
                }
            }
            s = resultString.toString();
            preparedStatement.close();
            conn.close();
            return s;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "deck not found";
    }

    public String selectOwnerToId(String id) {
        StringBuilder resultString = new StringBuilder();
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            SELECT "user" FROM "cards"
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

    public String updateOwnerToId(String id, String username) {
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE cards SET "user" = ?
            WHERE "id" = ?
            """);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, id);
            int resultInt = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            if(resultInt > 0) return "owner updated";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "card not found";
    }

    public String UnLockCard(String id, boolean lock){
        try {
            Connection conn = DBconnection.getInstance().getConn();
            PreparedStatement preparedStatement = conn.prepareStatement("""
            UPDATE cards SET "locked_for_trading" = ?
            WHERE "id" = ?
            """);
            preparedStatement.setBoolean(1, lock);
            preparedStatement.setString(2, id);
            int resultInt = preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
            if(resultInt > 0) return "card locked";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "card not found";
    }


}
