package com.example.controllers;

import com.example.Entities.UserEntity;
import com.example.services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

// user controller the parrent controller for admincontroller and customercontroller.
public class UserController {
    private UserService userService;
    private UserEntity userEntity;

    //    user controller
    public UserController() {
        try {
            this.userService = new UserService();
            this.userEntity = new UserEntity();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    //    new user Creation function
    public UserEntity createUser(Scanner sc) {
        System.out.println("For Creating User, Please enter user details:");

        UserEntity user = new UserEntity();

        System.out.print("Insert username: ");
        user.setUserName(sc.nextLine());

        System.out.print("Insert first name: ");
        user.setFirstName(sc.nextLine());

        System.out.print("Insert last name: ");
        user.setLastName(sc.nextLine());

        System.out.print("Insert email address: ");
        user.setEmail(sc.nextLine());

        System.out.print("Insert password: ");
        user.setPassword(sc.nextLine());

        System.out.print("Insert phone number: ");
        user.setPhone(sc.nextLine());

        // Save user to database
        try {
            if (userService != null) {
                UserEntity registeredUser = userService.registerUser(user);
                System.out.println("User registered successfully! ID: " + registeredUser.getUserId());
                return registeredUser;
            } else {
                System.out.println("Error: UserService is not initialized.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return null;
    }

    //    Loggin function
    public UserEntity Login(Scanner sc) throws SQLException {
        System.out.print("Please Insert Your Email: ");
        String email = sc.nextLine();
        System.out.print("Insert your password: ");
        String password = sc.nextLine();

        UserEntity user = userService.authenticateUser(email, password);
        if (user == null) {
            System.out.println("Error: User not found.");
            return null;
        } else {
            user.getUserDetails();
            System.out.println("Successfully logged in!");
            System.out.println("Welcome Back: " + user.getFirstName() + ' ' + user.getLastName());
            return user;
        }
    }

    //    Update user phone number function
    public void updatePhoneNumber(UserEntity user, Scanner sc) throws SQLException {
        System.out.print("Enter your new phone number: ");
        String newPhoneNumber = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        boolean verifyUpdate = userService.updatePhoneNumber(user, password, newPhoneNumber);
        if (verifyUpdate) {
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Phone number update failed.");
        }
    }

    //    change user password
    public void changePassword(UserEntity user, Scanner sc) throws SQLException {
        System.out.print("Enter your old password: ");
        String oldPassword = sc.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = sc.nextLine();
        boolean verifyPassword = userService.changePassword(user, newPassword, oldPassword);
        if (verifyPassword) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password change failed.");
        }
    }

    //    view user profile
    public void profile(UserEntity user) throws SQLException {
        System.out.println("\nYou Profile Details:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Role: " + user.getRole());
    }
}

