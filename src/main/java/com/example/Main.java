package com.example;


import com.example.Entities.UserEntity;
import com.example.controllers.AdminController;
import com.example.controllers.CustomerController;
import com.example.controllers.UserController;
import com.example.servlet.ConnectionTester;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        ConnectionTester db = new ConnectionTester();
        UserController userController = new UserController();
        Scanner sc = new Scanner(System.in);
        boolean wantToExit = false;

        System.out.println("Hello Dear!");
        System.out.println("Welcome to Chicken Production MS Application!");

        while (!wantToExit) {
            System.out.println("Please choose your option!");
            System.out.println("-->(1) for installation, \n-->(2) for Logging in \n-->(3) for registration \n-->(0) for exit");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    db.testConnections();
                    break;
                case 2:
                    UserEntity user = userController.Login(sc);
                    if (user != null) {
                        if (user.getRole().equals("admin")) {
                            AdminController adminController = new AdminController(user, sc);
                            adminController.menu();
                        } else {
                            CustomerController customerController = new CustomerController(user, sc);
                            customerController.menu();
                        }
                    }else{
                        System.out.println("Invalid username or password!");
                    }
                    break;
                case 3:
                    userController.createUser(sc); // Pass the Scanner object
                    break;
                case 0:
                    System.out.println("Comeback Again!");
                    wantToExit = true; // Exit the loop
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }

        sc.close(); // Close the scanner to avoid resource leak
    }

    public static void installation() {
        System.out.println("Installation");
    }

    public static void databaseConnectivityTest() {
        ConnectionTester connectionTester = new ConnectionTester();
        connectionTester.testConnections();
    }
}
