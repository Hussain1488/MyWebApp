package com.example.controllers;

import com.example.Entities.UserEntity;
import com.example.services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserController {
    private UserService userService;

    public UserController() {
        try {
            this.userService = new UserService(); // Initialize UserService
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    public UserEntity createUser(Scanner sc) {
        System.out.println("For Creating User, Please enter your details:");

        UserEntity user = new UserEntity();

        System.out.print("Insert your username: ");
        user.setUserName(sc.nextLine());

        System.out.print("Insert your first name: ");
        user.setFirstName(sc.nextLine());

        System.out.print("Insert your last name: ");
        user.setLastName(sc.nextLine());

        System.out.print("Insert your email address: ");
        user.setEmail(sc.nextLine());

        System.out.print("Insert your password: ");
        user.setPassword(sc.nextLine());

        System.out.print("Insert your phone number: ");
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

    public void Login(Scanner sc) throws SQLException {
        System.out.print("Please Insert Your Email: ");
        String email = sc.nextLine();
        System.out.print("Insert your password: ");
        String password = sc.nextLine();

        UserEntity user = userService.authenticateUser(email, password);
        if (user == null) {
            System.out.println("Error: User not found.");
        } else {
            user.getUserDetails();
            System.out.println("Successfully logged in!");
            System.out.println("Welcome Back: " + user.getFirstName() + ' ' + user.getLastName());
            if (user.getRole().equals("admin")) {
                AdminController admin = new AdminController(user, sc);
                admin.menu(); // Navigate to admin menu
            } else {
                // Handle regular user menu
                System.out.println("User menu not implemented yet.");
            }
        }
    }

    public void menu(UserEntity user) {
        System.out.println("Welcome to the user Menu");
    }
}

