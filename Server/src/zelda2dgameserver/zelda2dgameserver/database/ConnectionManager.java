package zelda2dgameserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String CONNECTION_STRING = "jdbc:postgresql://192.168.1.195:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "varsketis";

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
            Class.forName("org.postgresql.Driver");
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
