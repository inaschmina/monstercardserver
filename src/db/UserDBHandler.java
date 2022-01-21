package db;

import org.codehaus.jackson.JsonNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBHandler extends DBconnection{

    public String createUser(JsonNode credentials) {
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

    public String loginUser(JsonNode credentials) {
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
}
