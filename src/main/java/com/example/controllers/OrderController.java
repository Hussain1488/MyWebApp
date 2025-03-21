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
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner;
    private final UserEntity user;
    final private ProductService productService = new ProductService();
    private final OrderItemService orderItemService;
    private final UserService userService = new UserService();


    public OrderController(UserEntity user) throws SQLException {
        this.user = user;
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
        this.orderItemService = new OrderItemService();
    }


    public void orderMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nUser Order Menu:");
            System.out.println("(1)--> Create New Order");
            System.out.println("(2)--> View My Orders");
            System.out.println("(3)--> Update Order");
            System.out.println("(4)--> Delete Order");
            System.out.println("(0)--> Main Menu");


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
                    System.out.println("<--back");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createOrder() {
        try {
            if (orderService.hasUnpaidOrders(user.getUserId())) {
                System.out.println("You have unpaid orders. Please clear payments before creating a new order.");
                return;
            }

            OrderEntity newOrder = new OrderEntity(user.getUserId(), 0.0, "PENDING");
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
            List<OrderEntity> orders = orderService.getOrdersByUserId(user.getUserId());
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
            if (order == null || order.getUserId() != user.getUserId()) {
                System.out.println("Order not found or does not belong to you.");
                return;
            }
            if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED")) {
                System.out.print("Order has been " + order.getStatus() + ". You can't update this order. Please make another order.");
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
                    continue;
                }
                if(product.getStatus().equals(ProductEntity.Status.OUT_STOCK)){
                    System.out.println("This product is out of stock. Please make another product.\n");
                    continue;
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

                    order.setTotalAmount(order.getTotalAmount() + item.getPrice());

                    System.out.println("Item added successfully.");
                } else {
                    System.out.println("Failed to add item.");
                }
            }

            orderService.updateOrder(order);
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
            if (order == null || order.getUserId() != user.getUserId()) {
                System.out.println("Order not found or does not belong to you.");
                return;
            }

            if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED")) {
                System.out.print("Order has been " + order.getStatus() + ". You can't update this order.");
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

            System.out.print("Wants to cancel the order? (1) -> yes or (0) -> no: ");
            int cancel = scanner.nextInt();
            scanner.nextLine();
            if (cancel == 1) {
                OrderEntity cancelledOrder = orderService.getOrderById(orderId);
                order.setStatus("CANCELLED");
                if (orderService.updateOrder(cancelledOrder)) {
                    System.out.println("Order cancelled successfully.");
                } else {
                    System.out.println("Failed to cancell order.");
                }
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
            if (order == null || order.getUserId() != user.getUserId()) {
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


    public void adminMenu(UserEntity user, Scanner sc) throws SQLException {

        if (!user.getRole().equals("admin")) {
            System.out.println("You don't have permission to access this resource.");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\nUser Order Menu:");
            System.out.println("(1)--> Create New Order");
            System.out.println("(2)--> View all Orders");
            System.out.println("(3)--> Cancel user Order");
            System.out.println("(0)--> Back");

            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    createNewOrder(sc);
                    break;
                case 2:

                    viewAllOrders(sc, user);
                    break;
                case 3:
                    cancelUserOrder(sc);
                    break;
                case 0:
                    exit = true;
                    System.out.println("<--back");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createNewOrder(Scanner sc) throws SQLException {
        System.out.print("Enter User ID to create new order: ");
        int userId = sc.nextInt();
        UserEntity customer = userService.findUserById(userId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        OrderEntity order = new OrderEntity(customer.getUserId(), 0.0, "PENDING");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        order.setCreatedAt(currentTimestamp);
        order.setUpdatedAt(currentTimestamp);
        order.setPaidAmount(0.0);

        boolean orderCreated = orderService.createOrder(order);
        if (orderCreated) {
            System.out.println("Order created successfully with ID: " + order.getOrderId());
        } else {
            System.out.println("Failed to create order.");
        }
    }

    private void viewAllOrders(Scanner sc, UserEntity user) throws SQLException {

        System.out.println("Please select one option to filter orders: ");
        System.out.println("(1) --> All");
        System.out.println("(2) --> Pending");
        System.out.println("(3) --> Delivered");
        System.out.println("(4) --> Canceled");
        System.out.println("(5) --> Paid");
        System.out.println("(0) --> Back");

        int option = sc.nextInt();
        String filter = "PENDING";
        if (option == 2) {
            filter = "PENDING";
        } else if (option == 3) {
            filter = "DELIVERED";
            option = 2;
        } else if (option == 4) {
            filter = "CANCELLED";
            option = 2; }
        else if (option == 5) {
            filter = "PAID";
            option = 2;
        }
        switch (option) {
            case 1:
                orderService.getAllOrders(sc);
                break;
            case 2:
                orderService.getFilteredOrders(filter, sc);
                break;
            case 0:
                return;
        }


    }

    private void cancelUserOrder(Scanner sc) throws SQLException {

        System.out.print("Enter Order ID to update order: ");
        int orderId = sc.nextInt();
        OrderEntity order = orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getStatus().equals("DELIVERED")) {
            System.out.println("Cannot cancel a delivered order.");
            return;
        }

        order.setStatus("CANCELLED");
        boolean isUpdated = orderService.updateOrder(order);
        if (isUpdated) {
            System.out.println("Order updated successfully.");
        }else{
            System.out.println("Failed to update order.");
        }
    }
}
