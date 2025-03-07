package com.example.dao;

import com.example.Entities.OrderEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends DOA {

    public OrderDao() throws SQLException {
        super();
    }

    // Get all unpaid orders for a user
    public List<OrderEntity> getUnpaidOrders(int userId) throws SQLException {
        List<OrderEntity> unpaidOrders = new ArrayList<>();
        String query = "SELECT o.* FROM orders o " +
                "LEFT JOIN transactions t ON o.order_id = t.order_id " +
                "WHERE o.user_id = ? " +
                "GROUP BY o.order_id " +
                "HAVING SUM(t.amount) < o.total_amount OR COUNT(t.transaction_id) = 0";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderEntity order = new OrderEntity();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    unpaidOrders.add(order);
                }
            }
        }
        return unpaidOrders;
    }

    // Get all orders for a specific user
    public List<OrderEntity> getOrdersByUserId(int userId) throws SQLException {
        List<OrderEntity> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderEntity order = new OrderEntity();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaidAmount(rs.getDouble("paid_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    // Get an order by its ID
    public OrderEntity getOrderById(int orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderEntity order = new OrderEntity();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaidAmount(rs.getDouble("paid_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return order;
                }
            }
        }
        return null;
    }

    // Create a new order
    public boolean createOrder(OrderEntity order) throws SQLException {
        String query = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setOrderId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    // Update an existing order
    public boolean updateOrder(OrderEntity order) throws SQLException {
        String query = "UPDATE orders SET total_amount = ?, status = ? WHERE order_id = ? AND status != 'DELIVERED'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, order.getTotalAmount());
            stmt.setString(2, order.getStatus());
            stmt.setInt(3, order.getOrderId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete an order
    public boolean deleteOrder(int orderId) throws SQLException {
        // Step 1: Check the order status
        String checkOrderStatusQuery = "SELECT status FROM orders WHERE order_id = ?";
        try (PreparedStatement checkOrderStatusStmt = connection.prepareStatement(checkOrderStatusQuery)) {
            checkOrderStatusStmt.setInt(1, orderId);
            try (ResultSet rs = checkOrderStatusStmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");

                    // Step 2: Proceed only if the order status is not DELIVERED
                    if (!"DELIVERED".equals(status)) {
                        // Step 3: Delete related records in the order_items table
                        String deleteOrderItemsQuery = "DELETE FROM order_items WHERE order_id = ?";
                        try (PreparedStatement deleteOrderItemsStmt = connection.prepareStatement(deleteOrderItemsQuery)) {
                            deleteOrderItemsStmt.setInt(1, orderId);
                            deleteOrderItemsStmt.executeUpdate(); // Delete all related order items
                        }

                        // Step 4: Delete related records in the transactions table
                        String deleteOrderTransactionsQuery = "DELETE FROM transactions WHERE order_id = ?";
                        try (PreparedStatement deleteOrderTransactionsStmt = connection.prepareStatement(deleteOrderTransactionsQuery)) {
                            deleteOrderTransactionsStmt.setInt(1, orderId);
                            deleteOrderTransactionsStmt.executeUpdate(); // Delete all related transactions
                        }

                        // Step 5: Delete the order from the orders table
                        String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?";
                        try (PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrderQuery)) {
                            deleteOrderStmt.setInt(1, orderId);
                            return deleteOrderStmt.executeUpdate() > 0; // Delete the order
                        }
                    } else {
                        System.out.println("Cannot delete a delivered order.");
                        return false; // Order status is DELIVERED, so do not proceed
                    }
                } else {
                    System.out.println("Order not found.");
                    return false; // Order does not exist
                }
            }
        }
    }
}