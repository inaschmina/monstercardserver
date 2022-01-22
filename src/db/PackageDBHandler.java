package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageDBHandler extends DBconnection {

    public int insertPackage(int cost) {
        int returnValue = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO package (cost)
            VALUES (?)
            RETURNING id
            """);
            preparedStatement.setInt(1, cost);
            ResultSet rs= preparedStatement.executeQuery();
            rs.next();
            returnValue = rs.getInt(1);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return returnValue;
    }



    public int selectPackage() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT (id) FROM package
            ORDER BY "id"
            LIMIT 1
            """);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) return result.getInt(1);
            else return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void deletePackage(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM package
            WHERE id = ?
            """);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

}
