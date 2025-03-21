package com.example.controllers;


import com.example.Entities.UserEntity;

import com.example.services.OrderService;
import com.example.services.OrderItemService;
import com.example.services.ProductService;

import java.sql.SQLException;

import java.util.Scanner;

public class CustomerController extends UserController{

    private final Scanner sc;
    private final OrderService orderService;
    private final OrderController orderController;
    private final OrderItemService orderItemService;
    private final UserEntity customer;
    final private ProductService productService = new ProductService();

    public CustomerController(UserEntity customer, Scanner sc) throws SQLException {
        this.sc = sc;
        this.orderService = new OrderService();
        this.orderItemService = new OrderItemService();
        this.customer = customer;
        this.orderController = new OrderController(customer);

    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Menu:");
            System.out.println("(1)--> User profile");
            System.out.println("(2)--> My Orders");

            System.out.println("(0)--> Logout");


            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    orderController.orderMenu();
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

    private void userMenu() {

        boolean exit = false;

        while (!exit) {
            System.out.println("User Operations Menu:\n-->(1) Update Phone Number \n-->(2) Change Password \n-->(3) My Profile Details" +" \n-->(0) Back");
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