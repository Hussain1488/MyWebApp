package com.example.controllers;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;
import com.example.Entities.ProductEntity;
import com.example.Entities.UserEntity;
import com.example.services.OrderItemService;
import com.example.services.OrderService;
import com.example.services.ProductService;
import com.example.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner;
    private final UserEntity customer;
    final private ProductService productService = new ProductService();
    private final OrderItemService orderItemService;


    public OrderController(UserEntity customer) throws SQLException {
        this.customer = customer;
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
        this.orderItemService = new OrderItemService();
    }


    public void userMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Menu:");
            System.out.println("(1)--> Create New Order");
            System.out.println("(2)--> View My Orders");
            System.out.println("(3)--> Update Order");
            System.out.println("(4)--> Delete Order");
            System.out.println("(0)--> Logout");


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
                    updateOrder();
                    break;
                case 4:
                    deleteOrder();
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
                System.out.println("Wnat to add Items to the order? (1) --> yes, (0) --> no:");
                boolean wantAddItem = scanner.nextInt() == 1;
                if (wantAddItem) {
                    addItemsToOrder(newOrder.getOrderId());
                }

                System.out.println("Want to make payments? (1) --> yes, (0) --> no:");
                boolean wantPayment = scanner.nextInt() == 1;
                if (wantPayment) {
                    processPayment(newOrder.getOrderId());
                }
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
                System.out.println("Paid Amount: " + order.getPaidAmount());
                System.out.println("Status: " + order.getStatus());
                System.out.println("Created At: " + order.getCreatedAt());
                System.out.println("Updated At: " + order.getUpdatedAt());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
    }

    private void addItemsToOrder(int orderId) {

        System.out.println("\nAdding Items to order byId: " + orderId);

        try {
            OrderEntity order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != customer.getUserId()) {
                System.out.println("Order not found or does not belong to you.");
                return;
            }
            if (Objects.equals(order.getStatus(), "DELIVERED")) {
                System.out.print("Order has been delivered. You can't update this order. Please make another order.");
                return;
            }

            boolean addingItems = true;
            while (addingItems) {
                System.out.print("Enter Product ID (0 to finish): ");
                int productId = scanner.nextInt();
                if (productId == 0) {
                    addingItems = false;
                    continue;
                }

                ProductEntity product = productService.findProductById(productId);
                if (product == null) {
                    System.out.println("Product not found.");
                    return;
                }
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }

                scanner.nextLine();

                OrderItemEntity item = new OrderItemEntity(orderId, productId, quantity, product.getPrice() * quantity);
                if (orderItemService.addItemToOrder(item)) {
                    OrderEntity thisOrder = orderService.getOrderById(orderId);
                    thisOrder.setTotalAmount(thisOrder.getTotalAmount() + item.getPrice());
                    orderService.updateOrder(thisOrder);
                    System.out.println("Item added successfully.");
                } else {
                    System.out.println("Failed to add item.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding item: " + e.getMessage());
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


            System.out.print("Want to add items to order? (1) -> yes or (0) -> no: ");
            int addItems = scanner.nextInt();
            scanner.nextLine();
            if (addItems == 1) {
                addItemsToOrder(order.getOrderId());
            }

            System.out.println("Want to make a payment? (1) -> yes or (0) -> no: ");
            int payment = scanner.nextInt();
            scanner.nextLine();
            if (payment == 1) {
                processPayment(order.getOrderId());
            }


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

    private void processPayment(int orderId) {


        try {
            double outstanding = orderService.getOutstandingBalance(orderId);
            if (outstanding <= 0) {
                System.out.println("No outstanding balance for this order.");
                return;
            }

            System.out.printf("Outstanding Balance: %.2f%n", outstanding);
            System.out.print("Enter Payment Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

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
