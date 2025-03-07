package com.example.controllers;

import com.example.services.OrderService;

import java.sql.SQLException;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner;

    public OrderController() throws SQLException {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }

    public void handleOrderCreation(int userId) {
        try {
            int orderId = orderService.createNewOrder(userId);
            System.out.println("Order created with ID: " + orderId);
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    public void handleAddProductToOrder(int orderId) {
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price per item: ");
        double price = scanner.nextDouble();

        try {
            boolean success = orderService.addProductToOrder(orderId, productId, quantity, price);
            if (success) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
        }
    }

    public void handleOrderPayment(int orderId) {
        System.out.print("Enter payment amount: ");
        double paymentAmount = scanner.nextDouble();

        try {
            boolean success = orderService.payOrder(orderId, paymentAmount);
            if (success) {
                System.out.println("Payment successful.");
            } else {
                System.out.println("Payment failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }

    public void handleOrderDeletion(int orderId) {
        try {
            boolean success = orderService.deleteOrder(orderId);
            if (success) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("Failed to delete order.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }
}
