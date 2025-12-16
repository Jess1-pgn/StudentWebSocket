package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class.
 * Provides centralized database connection management.
 */
public class DatabaseConnection {
    
    // Database connection parameters
    // TODO: In production, these should be externalized to a configuration file or environment variables
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Static block to load the MySQL JDBC driver
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    /**
     * Gets a connection to the database.
     * @return Connection object
     * @throws SQLException if connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Closes a database connection safely.
     * @param connection the connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tests the database connection.
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
