package com.example.services;

import com.example.Entities.UserEntity;
import com.example.dao.UserDAOImp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

// user service class
public class UserService {
    private UserDAOImp userDAO;


    public UserService() throws SQLException {
        this.userDAO = new UserDAOImp();

    }

    public UserService(UserDAOImp userDAO) throws SQLException {
        this.userDAO = userDAO;
    }

    //    method for user registratioin.
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

    //    User Athentication.
    public UserEntity authenticateUser(String email, String password) throws SQLException {
        UserEntity user = userDAO.getUserByEmail(email);
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user; // Authentication successful
        }
        return null; // Authentication failed
    }

    //    user password verification method
    private boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return hashPassword(enteredPassword).equals(storedHashedPassword);
    }

    //    hashing password method.
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

    //    password change functions.
    public boolean changePassword(UserEntity user, String newPassword, String oldPassword) throws SQLException {
        boolean verifyPassword = verifyPassword(oldPassword, user.getPassword());
        if (verifyPassword) {
            user.setPassword(hashPassword(newPassword));
        }
        return verifyPassword;
    }

    //    update user phone number
    public boolean updatePhoneNumber(UserEntity user, String password, String phoneNumber) throws SQLException {
        boolean verifyPassword = verifyPassword(password, user.getPassword());
        if (verifyPassword) {
            user.setPhone(phoneNumber);
            userDAO.updateUser(user);
        }
        return verifyPassword;
    }

    //    Getting user list
    public List<UserEntity> getUsersWithPagination(int limit, int offset) throws SQLException {
        return userDAO.getUsersWithLimit(limit, offset);
    }

    //    getting user by id
    public UserEntity findUserById(int userId) throws SQLException {
        return userDAO.getUserById(userId);
    }

    //    deleting user
    public boolean deleteUser(int userId) throws SQLException {
        return userDAO.deleteUser(userId);
    }

    //    Updating user method.
    public UserEntity updateUser(UserEntity user) throws SQLException {
        UserEntity updatedUser = userDAO.updateUser(user);
        return updatedUser;
    }
}