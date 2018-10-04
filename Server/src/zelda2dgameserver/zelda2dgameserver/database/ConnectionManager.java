package zelda2dgameserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String CONNECTION_STRING = "jdbc:mysql://88.222.22.14:3306/zelda";
    private static final String USER = "zelda";
    private static final String PASS = "zeldagamers";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        return connect();
    }

    private static Connection connect() {
        Connection connection = null;

        System.out.println("Connecting to Postgresql...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
            ConnectionManager.connection = connection;
            System.out.println("Connected.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Postgresql driver not found.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not connect to Postgresql server.");
        }

        return connection;
    }
}
