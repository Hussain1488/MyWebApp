package com.example.controllers;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;
import com.example.Entities.TransactionEntity;
import com.example.services.OrderService;
import com.example.services.OrderItemService;
import com.example.services.TransactionService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    private final Scanner sc;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final TransactionService transactionService;

    public OrderController(Scanner sc) throws SQLException {
        this.sc = sc;
        this.orderService = new OrderService();
        this.orderItemService = new OrderItemService();
        this.transactionService = new TransactionService();
    }

    public void createOrder(int userId) {
        System.out.print("Enter total amount: ");
        double totalAmount = sc.nextDouble();
        sc.nextLine(); // Consume newline

        OrderEntity order = new OrderEntity(userId, totalAmount, "PENDING");

        try {
            if (orderService.createOrder(order)) {
                System.out.println("Order created successfully!");
            } else {
                System.out.println("Failed to create order.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    public void updateOrder(int orderId) {
        try {
            OrderEntity order = orderService.findOrderById(orderId);
            if (order == null) {
                System.out.println("Order not found.");
                return;
            }

            System.out.print("Enter new total amount (leave blank to keep current): ");
            String totalAmountStr = sc.nextLine();
            if (!totalAmountStr.isEmpty()) {
                order.setTotalAmount(Double.parseDouble(totalAmountStr));
            }

            System.out.print("Enter new status (PENDING, PROCESSING, DELIVERED, CANCELLED): ");
            String status = sc.nextLine();
            if (!status.isEmpty()) {
                order.setStatus(status);
            }

            if (orderService.updateOrder(order)) {
                System.out.println("Order updated successfully!");
            } else {
                System.out.println("Failed to update order.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    public void deleteOrder(int orderId) {
        try {
            if (orderService.deleteOrder(orderId)) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Failed to delete order.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }

//    public void addOrderItem(int orderId) {
//        System.out.print("Enter product ID: ");
//        int productId = sc.nextInt();
//        System.out.print("Enter quantity: ");
//        int quantity = sc.nextInt();
//        System.out.print("Enter price per unit: ");
//        double price = sc.nextDouble();
//        sc.nextLine(); // Consume newline
//
//        OrderItemEntity orderItem = new OrderItemEntity(orderId, productId, quantity, price);
//
//        try {
//            if (orderItemService.addOrderItem(orderItem)) {
//                System.out.println("Order item added successfully!");
}