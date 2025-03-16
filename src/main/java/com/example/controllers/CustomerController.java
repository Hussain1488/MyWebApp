package com.example.controllers;


import com.example.Entities.UserEntity;

import com.example.services.OrderService;
import com.example.services.OrderItemService;
import com.example.services.ProductService;

import java.sql.SQLException;

import java.util.Scanner;

public class CustomerController {

    private final Scanner scanner;
    private final OrderService orderService;
    private final OrderController orderController;
    private final OrderItemService orderItemService;
    private final UserEntity customer;
    final private ProductService productService = new ProductService();

    public CustomerController(UserEntity customer, Scanner scanner) throws SQLException {
        this.scanner = scanner;
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


            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
//                    createOrder();
                    break;
                case 2:
                    orderController.userMenu();
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

}