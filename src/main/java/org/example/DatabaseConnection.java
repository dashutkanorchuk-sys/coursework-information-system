package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/bus";
    private static final String USER = "darianorchuk";
    private static final String PASSWORD = "12212005";

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to database successfully!");
            return conn;
        } catch (Exception e) {
            System.out.println("❌ Database connection ERROR: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
