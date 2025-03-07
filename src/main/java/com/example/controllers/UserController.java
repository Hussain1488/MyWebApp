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

    public void menu(UserEntity user) {
        System.out.println("Welcome to the user Menu");
    }
}

