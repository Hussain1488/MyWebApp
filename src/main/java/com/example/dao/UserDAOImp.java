package com.example.dao;

import com.example.Entities.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp extends DOA implements UserDOA {


    public UserDAOImp() throws SQLException {
        super();
    }

    // Check if an admin user exists
    public boolean adminExists() throws SQLException {
        String query = "SELECT * FROM users WHERE role = 'admin'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if an admin exists
        }
    }

    // Getting admin with
    private static UserEntity getAdmin(UserEntity admin) {
        return admin;
    }

    // Creating User
    @Override
    public boolean createUser(UserEntity user) throws SQLException {

//        String countQuery = "SELECT COUNT(*) FROM users";
//        int userCount = 0;
//
//        try (PreparedStatement countStatement = connection.prepareStatement(countQuery);
//             ResultSet resultSet = countStatement.executeQuery()) {
//            if (resultSet.next()) {
//                userCount = resultSet.getInt(1); // Get the number of existing users
//            }
//        }

        // Assign role based on user count
        String userRole = !adminExists() ? "admin" : "customer";
        user.setRole(userRole); // Set the role in the user object

        String query = "INSERT INTO users (user_name, first_name, last_name, email, password, phone_number, role, updated_at, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhone());
            statement.setString(7, userRole);
            statement.setTimestamp(8, currentTimestamp);
            statement.setTimestamp(9, currentTimestamp);

            return statement.executeUpdate() > 0; // Returns true if insert was successful
        }
    }

    // Getting Uesr By Id
    @Override
    public UserEntity getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        }
        return null; // User not found
    }

    // Retrieve a user by email
    @Override
    public UserEntity getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }
        }
        return null;
    }


    @Override
    public UserEntity updateUser(UserEntity user) throws SQLException {
        // Validate input
        if (user == null || user.getUserId() == 0) {
            throw new IllegalArgumentException("User object or user ID cannot be null/zero.");
        }

        // Define the SQL query
        String query = "UPDATE users SET user_name = ?, first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ?, role = ?, updated_at = ? WHERE user_id = ?";
        // Create a timestamp for the current time
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters for the PreparedStatement
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getRole());
            statement.setTimestamp(8, currentTimestamp);
            statement.setInt(9, user.getUserId());

            // Execute the update
            int rowsUpdated = statement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                // Fetch and return the updated user
                return getUserById(user.getUserId());
            } else {
                // No rows were updated (e.g., user_id not found)
                throw new SQLException("Failed to update user. User ID not found or no changes made.");
            }
        } catch (SQLException e) {
            // Log the error and rethrow the exception
            System.err.println("Error updating user: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0; // Return true if delete was successful
        }
    }

    public List<UserEntity> getUsersWithLimit(int limit, int offset) throws SQLException {
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";
        List<UserEntity> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
        }
        return users;
    }

    private UserEntity extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        UserEntity user = new UserEntity();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUserName(resultSet.getString("user_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setPhone(resultSet.getString("phone_number"));
        user.setRole(resultSet.getString("role"));
        user.setUpdatedOn(resultSet.getTimestamp("updated_at"));
        user.setCreatedOn(resultSet.getTimestamp("created_at"));
        return user;
    }

}