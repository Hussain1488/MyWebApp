package com.example.controllers;

import com.example.Entities.UserEntity;
import com.example.dao.UserDAOImp;
import com.example.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    private Scanner sc;
    private UserEntity user;
    private UserService userService;

    public AdminController(UserEntity user, Scanner sc) {
        this.user = user;
        this.sc = sc;
        try {
            this.userService = new UserService(); // Initialize UserService
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:\n-->(1) For Users Operations \n-->(2) For Orders Operations \n-->(0) Back");
            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    System.out.println("Orders operations not implemented yet.");
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting admin menu.");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }

    private void userMenu() {
        System.out.println("User Operations Menu:\n-->(1) Update Phone Number \n-->(2) Change Password \n-->(3) List of Users" +
                " \n-->(4) Find a User \n-->(5) For Delete User Operations \n-->(6) Edit User" +
                "\n-->(7) Create User\n-->(0) Back to Admin Menu");
        int option = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        boolean exit = false;

        while (!exit) {
            switch (option) {
                case 1:
                    try {
                        updatePhoneNumber();
                    } catch (SQLException e) {
                        System.err.println("Error updating phone number: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        changePassword();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        userList();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        findUser();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        deleteUser();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        editUser();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        createUser();
                    } catch (SQLException e) {
                        System.err.println("Error changing password: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Returning to admin menu.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }


    private void changePassword() throws SQLException {
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

    private void updatePhoneNumber() throws SQLException {
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

    private void userList() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int limit = 5; // Number of users to display per page
        int offset = 0; // Starting point for fetching users

        while (true) {
            // Fetch users with the current limit and offset
            List<UserEntity> users = userService.getUsersWithPagination(limit, offset);

            // Display the users
            System.out.println("Users List (Page " + (offset / limit + 1) + "):");
            for (UserEntity user : users) {
                System.out.println("ID: " + user.getUserId() + ", Name: " + user.getFirstName() + " " + user.getLastName());
            }

            // Prompt the user for input
            System.out.println("\nEnter 'n' for next page, 'p' for previous page, or 'q' to quit:");
            String input = scanner.nextLine().trim().toLowerCase();

            // Handle user input
            if (input.equals("n")) {
                offset += limit; // Move to the next page
            } else if (input.equals("p")) {
                if (offset >= limit) {
                    offset -= limit; // Move to the previous page
                } else {
                    System.out.println("You are already on the first page.");
                }
            } else if (input.equals("q")) {
                break; // Exit the loop
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        scanner.close();
    }


    private void findUser() throws SQLException {
        System.out.print("Enter user ID: ");
    }

    private void deleteUser() throws SQLException {
        System.out.print("Enter user ID: ");
    }

    private void editUser() throws SQLException {
        System.out.print("Enter user ID: ");

    }

    private void createUser() throws SQLException {
        System.out.print("Enter user ID: ");

    }

}


