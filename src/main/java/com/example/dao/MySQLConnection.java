package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/checken_production";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "MarTin1488"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static String checkConnectionStatus() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure driver is loaded
            try (Connection connection = getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    return "MySQL Connection: Successful";
                }
            }
        } catch (ClassNotFoundException e) {
            return "MySQL Connection: Failed - Driver not found: " + e;
        } catch (SQLException e) {
            return "MySQL Connection: Failed - " + e;
        }
        return "MySQL Connection: Unknown Status";
    }

}