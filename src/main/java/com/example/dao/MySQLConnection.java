package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//MySQL Connection setting.
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_app";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "MarTin1488"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

//    Checking the connection connectivity.
    public static void checkConnectionStatus() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure driver is loaded
            try (Connection connection = getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    System.out.println("Connected to MySQL database successfully");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connection: Failed - Driver not found: " + e);
        } catch (SQLException e) {
            System.out.println("MySQL Connection: Failed - " + e);
        }
    }

}