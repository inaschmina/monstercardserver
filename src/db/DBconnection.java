package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    protected static DBconnection connection = null;
    private DBconnection() throws SQLException {
    }

    public static DBconnection getInstance() throws SQLException {
        if(connection == null) {
             connection = new DBconnection();

        }
        return connection;
    }

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/swe1user", "swe1user", "swe1pw");
    }
}
