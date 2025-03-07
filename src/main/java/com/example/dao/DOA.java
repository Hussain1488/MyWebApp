package com.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DOA {

    protected Connection connection; // Changed to protected for subclass access
    Timestamp currentTimestamp;

    public DOA() throws SQLException {
        try {
            // Initialize the database connection
            this.connection = MySQLConnection.getConnection();
            currentTimestamp = new Timestamp(System.currentTimeMillis());

        } catch (SQLException e) {
            // Log the error or rethrow a custom exception
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            throw e; // Rethrow the exception to indicate failure
        }
    }

    // Optional: Add a method to close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}