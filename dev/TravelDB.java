package dev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class TravelDB {

    private static final String URL = "jdbc:mysql://localhost:3306/travelwishlist_db";
    private static final String USER = "root";
    private static final String PASS = readPasswordFromFile();

    // Method 1: Read password from file (security improvement)
    private static String readPasswordFromFile() {
        try {
            return new String(Files.readAllBytes(Paths.get("db_password.txt"))).trim();
        } catch (IOException e) {
            throw new RuntimeException("Create db_password.txt with your database password", e);
        }
    }

    // Method 2: Your existing connection method
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Method 3: NEW - Check if database is connected
    public static boolean isDatabaseConnected() {
        try (Connection conn = connect()) {
            System.out.println("✅ Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }

    // Method 4: close method 
    public static void close(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception e) {
                    System.err.println("⚠️ Warning: Failed to close resource: " + e.getMessage());
                }
            }
        }
    }
}