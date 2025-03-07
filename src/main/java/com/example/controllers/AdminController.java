package com.example.controllers;

import com.example.Entities.UserEntity;
import com.example.dao.UserDAOImp;
import com.example.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminController extends UserController {
    final private Scanner sc;
    final private UserEntity user;
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

    public void menu() throws SQLException {
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:\n-->(1) For Users Operations \n-->(2) For Products" +
                    " \n-->(3) For Orders \n-->(0) Back");
            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    ProductController productController = new ProductController(sc, user);
                    productController.menu();
                    break;
                case 3:
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

        boolean exit = false;

        while (!exit) {
            System.out.println("User Operations Menu:\n-->(1) Update Phone Number \n-->(2) Change Password \n-->(3) List of Users" + " \n-->(4) Find a User \n-->(5) For Delete User Operations \n-->(6) Edit User" + "\n-->(7) Create User\n-->(0) Back to Admin Menu");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); // Clear invalid input
            }
            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character
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
        int limit = 5; // Number of users to display per page
        int offset = 0; // Starting point for fetching users

        while (true) {
            // Fetch users with the current limit and offset
            List<UserEntity> users = userService.getUsersWithPagination(limit, offset);

            // Display the users
            System.out.println("Users List (Page " + (offset / limit + 1) + "):");
            for (UserEntity user : users) {
                System.out.println("ID: " + user.getUserId() + ", Name: " + user.getFirstName() + " " + user.getLastName() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
            }

            // Prompt the user for input
            System.out.println("\nEnter 'n' for next page, 'p' for previous page, or 'q' to quit:");
            String input = sc.nextLine().trim().toLowerCase();

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
    }

    private void findUser() throws SQLException {
        System.out.print("Enter user ID: ");
        int userId = sc.nextInt(); // Read the user ID from input

        // Fetch the user from the database
        UserEntity user = userService.findUserById(userId);

        if (user != null) {
            // Display user details
            System.out.println("\nUser Found:");
            System.out.println("ID: " + user.getUserId());
            System.out.println("Username: " + user.getUserName());
            System.out.println("First Name: " + user.getFirstName());
            System.out.println("Last Name: " + user.getLastName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Role: " + user.getRole());
            System.out.println("Created On: " + user.getCreatedOn());
            System.out.println("Updated On: " + user.getUpdatedOn());
        } else {
            System.out.println("User not found with ID: " + userId);
        }

    }

    private void deleteUser() throws SQLException {
        System.out.print("Enter user ID to delete: ");
        System.out.println("Enter (0) for Cancel :");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid user ID:");
            sc.next(); // Clear invalid input
        }
        int userId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        if (userId == 0) {
            return;
        }

        if (user.getUserId() == userId) {
            System.out.println("You are not allowed to delete this user!");
            return;
        }

        // Confirm deletion
        System.out.print("Are you sure you want to delete user with ID " + userId + "? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            // Attempt to delete the user
            boolean isDeleted = userService.deleteUser(userId);

            if (isDeleted) {
                System.out.println("User with ID " + userId + " has been deleted successfully.");
            } else {
                System.out.println("User with ID " + userId + " not found or could not be deleted.");
            }
        } else {
            System.out.println("Deletion canceled.");
        }


    }

    private void editUser() throws SQLException {
        // Prompt for user ID
        System.out.print("Enter user ID to edit: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid user ID:");
            sc.next(); // Clear invalid input
        }
        int userId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        // Fetch the user from the database
        UserEntity user = userService.findUserById(userId);

        if (user == null) {
            System.out.println("User with ID " + userId + " not found.");
            return; // Exit the method if the user doesn't exist
        }

        // Display current user details
        System.out.println("\nCurrent User Details:");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());

        // Prompt for updated details
        System.out.println("\nEnter new details (leave blank to keep current value):");

        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        if (!username.isEmpty()) {
            user.setUserName(username);
        }

        System.out.print("First Name: ");
        String firstName = sc.nextLine().trim();
        if (!firstName.isEmpty()) {
            user.setFirstName(firstName);
        }

        System.out.print("Last Name: ");
        String lastName = sc.nextLine().trim();
        if (!lastName.isEmpty()) {
            user.setLastName(lastName);
        }

        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();
        if (!phone.isEmpty()) {
            user.setPhone(phone);
        }

        // Confirm update
        System.out.print("Are you sure you want to update user with ID " + userId + "? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes")) {
            // Attempt to update the user
            UserEntity updatedUser = userService.updateUser(user);

            if (updatedUser != null) {
                System.out.println("User with ID " + userId + " has been updated successfully.");
            } else {
                System.out.println("User with ID " + userId + " could not be updated.");
            }
        } else {
            System.out.println("Update canceled.");
        }
    }

    private void createUser() throws SQLException {
        System.out.print("Enter user ID: ");

    }


}


