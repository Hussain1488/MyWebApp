package com.example.dao;

import com.example.Entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // Check if an admin user exists
    public boolean adminExists() throws SQLException {
        String query = "SELECT * FROM users WHERE role = 'admin'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if an admin exists
        }
    }

    // Create a new admin user
    public void createAdmin(UserEntity admin) throws SQLException {
        // Get the current timestamp

        String query = "INSERT INTO users (user_name, first_name, last_name, email, password, phone_number, role, updated_at, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getUserName());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getLastName());
            statement.setString(4, admin.getEmail());
            statement.setString(5, admin.getPassword());
            statement.setString(6, admin.getPhone());
            statement.setString(7, getAdmin(admin).getRole());
            statement.setTimestamp(8, admin.getUpdatedOn());
            statement.setTimestamp(9, admin.getCreatedOn());
            statement.executeUpdate();
        }
    }

    private static UserEntity getAdmin(UserEntity admin) {
        return admin;
    }
}