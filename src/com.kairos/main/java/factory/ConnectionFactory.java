package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL  = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "C##kairos";
    private static final String PASS = "k123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
