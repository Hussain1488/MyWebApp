package com.example.controllers;


import com.example.Entities.UserEntity;

import com.example.services.OrderService;
import com.example.services.OrderItemService;
import com.example.services.ProductService;

import java.sql.SQLException;

import java.util.Scanner;

//Conroller for user with customer role, can create order, see products, edit their orders, and so on.
public class CustomerController extends UserController {

    private final Scanner sc;
    private final OrderService orderService;
    private final OrderController orderController;
    private final OrderItemService orderItemService;
    private final UserEntity customer;
    final private ProductService productService = new ProductService();

    //    CustomerController constructore.
    public CustomerController(UserEntity customer, Scanner sc) throws SQLException {
        this.sc = sc;
        this.orderService = new OrderService();
        this.orderItemService = new OrderItemService();
        this.customer = customer;
        this.orderController = new OrderController(customer);

    }

    //    main menu for customer.
    public void menu() {
        boolean exit = false;
        int option = -1;
        while (!exit) {
            System.out.println("\nCustomer Menu:");
            System.out.println("(1)--> User profile");
            System.out.println("(2)--> My Orders");

            System.out.println("(0)--> Logout");


            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.next();
                continue;
            }

            switch (option) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    orderController.orderMenu(sc);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //    menu for user profile operations.
    private void userMenu() {

        boolean exit = false;

        while (!exit) {
            System.out.println("User Operations Menu:\n-->(1) Update Phone Number \n-->(2) Change Password \n-->(3) My Profile Details" + " \n-->(0) Back");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next();
            }

            int option = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            switch (option) {
                case 1:
                    try {
                        super.updatePhoneNumber(customer, sc);
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        super.changePassword(customer, sc);
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        super.profile(customer);
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Going back.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}