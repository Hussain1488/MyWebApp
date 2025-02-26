package com.example;

import com.example.Entities.UserEntity;
import com.example.controllers.UserController;
import com.example.dao.MySQLConnection;
import com.example.dao.UserDao;
import com.example.servlet.ConnectionTester;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {




//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//
//        System.out.println(currentTimestamp);
        ConnectionTester db = new ConnectionTester();
        UserController user = new UserController();


        Scanner sc = new Scanner(System.in);
        boolean wantToExit = false;

        System.out.println("Hello Dear!");
        System.out.println("Welcome to Checken Production MS Application!");

        while (!wantToExit) {
            System.out.println("Please choose your option!");
            System.out.println("-->(1) for installation, \n-->(2) for starting application \n-->(3) for exit");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    db.testConnections();

                    break;
                case 2:
                    System.out.println("You have successfully started!");
                    start();
                    break;
                case 3:
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


    public static void start() {

    }
}
