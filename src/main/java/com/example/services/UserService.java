package com.example.services;

import com.example.Entities.UserEntity;
import com.example.dao.MySQLConnection;
import com.example.dao.UserDAOImp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAOImp userDAO;
    Connection connection;

    public UserService() throws SQLException {
        connection = MySQLConnection.getConnection();
        this.userDAO = new UserDAOImp(connection); // Ensure UserDAOImp is initialized
    }

    public UserEntity registerUser(UserEntity newUser) throws SQLException {
        // Check if user already exists (by email)
        if (userDAO.getUserByEmail(newUser.getEmail()) != null) {
            throw new IllegalArgumentException("Error: Email is already registered.");
        }

        // Hash the password before storing it
        newUser.setPassword(hashPassword(newUser.getPassword()));

        // Assign a default role (e.g., "user")
        if (newUser.getRole() == null || newUser.getRole().isEmpty()) {
            newUser.setRole("customer");
        }

        // Save the user in the database
        if (userDAO.createUser(newUser)) {
            return userDAO.getUserByEmail(newUser.getEmail());
        }
            return null;
    }

    public UserEntity authenticateUser(String email, String password) throws SQLException {
        UserEntity user = userDAO.getUserByEmail(email);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user; // Authentication successful
        }
        return null; // Authentication failed
    }

    private boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return hashPassword(enteredPassword).equals(storedHashedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean changePassword(UserEntity user, String newPassword, String oldPassword) throws SQLException {
        boolean verifyPassword = verifyPassword(oldPassword, user.getPassword());
        if (verifyPassword) {
            user.setPassword(hashPassword(newPassword));
        }
        return verifyPassword;
    }

    public boolean updatePhoneNumber(UserEntity user, String password, String phoneNumber) throws SQLException {
        boolean verifyPassword = verifyPassword(password, user.getPassword());
        if (verifyPassword) {
            user.setPhone(phoneNumber);
            userDAO.updateUser(user);
        }
        return verifyPassword;
    }

    public List<UserEntity> getUsersWithPagination(int limit, int offset) throws SQLException {
        return userDAO.getUsersWithLimit(limit, offset);
    }
}