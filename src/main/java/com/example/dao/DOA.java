package com.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DOA {

    protected Connection connection;
    Timestamp currentTimestamp;

    // Initialize the database connection
    public DOA() throws SQLException {
        try {
            this.connection = MySQLConnection.getConnection();
            currentTimestamp = new Timestamp(System.currentTimeMillis());

        } catch (SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            throw e;
        }
    }

    // Add a method to close the connection
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