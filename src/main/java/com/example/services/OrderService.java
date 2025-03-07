package com.example.services;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;
import com.example.Entities.TransactionEntity;
import com.example.dao.OrderDao;
import com.example.dao.OrderItemDao;
import com.example.dao.TransactionDao;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    private final TransactionDao transactionDao;
    private OrderItemDao orderItem;

    public OrderService(OrderItemDao orderItem) throws SQLException {
        this.orderItem = orderItem;
        this.orderDao = new OrderDao();
        this.transactionDao = new TransactionDao();
    }

    public OrderService() throws SQLException {
        this.orderDao = new OrderDao();
        this.transactionDao = new TransactionDao();
    }


    // Check if the user has any unpaid orders
    public boolean hasUnpaidOrders(int userId) throws SQLException {
        List<OrderEntity> unpaidOrders = orderDao.getUnpaidOrders(userId);
        return !unpaidOrders.isEmpty();
    }

    // Get all orders for a specific user
    public List<OrderEntity> getOrdersByUserId(int userId) throws SQLException {
        return orderDao.getOrdersByUserId(userId);
    }

    // Get an order by its ID
    public OrderEntity getOrderById(int orderId) throws SQLException {
        return orderDao.getOrderById(orderId);
    }

    // Add a payment for an order
    public boolean addPayment(int orderId, double amount) throws SQLException {
        return transactionDao.addTransaction(new TransactionEntity(orderId, amount));
    }

    // Calculate the outstanding balance for an order
    public double getOutstandingBalance(int orderId) throws SQLException {
        double totalAmount = orderDao.getOrderById(orderId).getTotalAmount();
        double paidAmount = transactionDao.getTotalPaid(orderId);
        return totalAmount - paidAmount;
    }

    // Create a new order
    public boolean createOrder(OrderEntity order) throws SQLException {
        return orderDao.createOrder(order);
    }

    // Update an existing order
    public boolean updateOrder(OrderEntity order) throws SQLException {
        return orderDao.updateOrder(order);
    }

    // Delete an order
    public boolean deleteOrder(int orderId) throws SQLException {
        return orderDao.deleteOrder(orderId);
    }
    public boolean addProductToOrder(int orderId, int productId, int quantity, double price) throws SQLException {
        // Create an OrderItemEntity object
        OrderItemEntity item = new OrderItemEntity(orderId, productId, quantity, price);

        // Add the item to the order
        return orderItem.addOrderItem(item);
    }

    public boolean payOrder(int orderId, double amount) throws SQLException {
        // Check if the order exists
        OrderEntity order = orderDao.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return false;
        }

        // Add the payment transaction
        TransactionEntity transaction = new TransactionEntity(orderId, amount);
        if (!transactionDao.addTransaction(transaction)) {
            System.out.println("Failed to process payment.");
            return false;
        }

        // Check if the order is fully paid
        double totalAmount = order.getTotalAmount();
        double paidAmount = transactionDao.getTotalPaid(orderId);
        if (paidAmount >= totalAmount) {
            // Update the order status to "PAID" or "COMPLETED"
            order.setStatus("PAID");
            orderDao.updateOrder(order);
        }

        System.out.println("Payment processed successfully.");
        return true;
    }

}