package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_app";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "MarTin1488"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static String checkConnectionStatus() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                return "MySQL Connection: Successful";
            }
        } catch (SQLException e) {
            return "MySQL Connection: Failed - " + e.getMessage();
        }
        return "MySQL Connection: Unknown Status";
    }
}