package com.example.controllers;

import com.example.Entities.OrderEntity;
import com.example.Entities.UserEntity;
import com.example.services.OrderService;
import com.example.services.OrderItemService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerController {

    private final Scanner scanner;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserEntity customer;

    public CustomerController(UserEntity customer, Scanner scanner) throws SQLException {
        this.scanner = scanner;
        this.orderService = new OrderService();
        this.orderItemService = new OrderItemService();
        this.customer = customer;
    }

    public void menu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Create New Order");
            System.out.println("2. View My Orders");
            System.out.println("3. Add Items to Order");
            System.out.println("4. Update Order");
            System.out.println("5. Delete Order");
            System.out.println("6. Make Payment");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    addItemsToOrder();
                    break;
                case 4:
                    updateOrder();
                    break;
                case 5:
                    deleteOrder();
                    break;
                case 6:
                    processPayment();
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

    private void createOrder() {
        try {
            if (orderService.hasUnpaidOrders(customer.getUserId())) {
                System.out.println("You have unpaid orders. Please clear payments before creating a new order.");
                return;
            }

            OrderEntity newOrder = new OrderEntity(customer.getUserId(), 0.0, "PENDING");
            if (orderService.createOrder(newOrder)) {
                System.out.println("Order created successfully! Order ID: " + newOrder.getOrderId());
            } else {
                System.out.println("Failed to create order.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    private void viewOrders() {
        try {
            List<OrderEntity> orders = orderService.getOrdersByUserId(customer.getUserId());
            if (orders.isEmpty()) {
                System.out.println("No orders found.");
                return;
            }

            System.out.println("\nYour Orders:");
            for (OrderEntity order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Total Amount: " + order.getTotalAmount());
                System.out.println("Status: " + order.getStatus());
                System.out.println("Created At: " + order.getCreatedAt());
                System.out.println("Updated At: " + order.getUpdatedAt());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
    }

    private void addItemsToOrder() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean addingItems = true;
        while (addingItems) {
            System.out.print("Enter Product ID (0 to finish): ");
            int productId = scanner.nextInt();
            if (productId == 0) {
                addingItems = false;
                continue;
            }

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();

            System.out.print("Enter Price per Unit: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            try {
                OrderItemEntity item = new OrderItemEntity(orderId, productId, quantity, price);
                if (orderItemService.addItemToOrder(item)) {
                    System.out.println("Item added successfully.");
                } else {
                    System.out.println("Failed to add item.");
                }
            } catch (SQLException e) {
                System.err.println("Error adding item: " + e.getMessage());
            }
        }
    }

    private void updateOrder() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            OrderEntity order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != customer.getUserId()) {
                System.out.println("Order not found or does not belong to you.");
                return;
            }

            if (order.getStatus().equals("DELIVERED")) {
                System.out.println("Cannot update a delivered order.");
                return;
            }

            System.out.print("Enter New Total Amount: ");
            double totalAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter New Status (PENDING, PROCESSING, CANCELLED): ");
            String status = scanner.nextLine();

            order.setTotalAmount(totalAmount);
            order.setStatus(status);

            if (orderService.updateOrder(order)) {
                System.out.println("Order updated successfully.");
            } else {
                System.out.println("Failed to update order.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    private void deleteOrder() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            OrderEntity order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != customer.getUserId()) {
                System.out.println("Order not found or does not belong to you.");
                return;
            }

            if (order.getStatus().equals("DELIVERED")) {
                System.out.println("Cannot delete a delivered order.");
                return;
            }

            if (orderService.deleteOrder(orderId)) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("Failed to delete order.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }

    private void processPayment() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            double outstanding = orderService.getOutstandingBalance(orderId);
            if (outstanding <= 0) {
                System.out.println("No outstanding balance for this order.");
                return;
            }

            System.out.printf("Outstanding Balance: %.2f%n", outstanding);
            System.out.print("Enter Payment Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (amount > outstanding) {
                System.out.println("Payment amount exceeds outstanding balance.");
                return;
            }

            if (orderService.addPayment(orderId, amount)) {
                System.out.println("Payment processed successfully.");
            } else {
                System.out.println("Failed to process payment.");
            }
        } catch (SQLException e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }
}