package com.example.dao;

import com.example.Entities.OrderEntity;
import com.example.Entities.OrderItemEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends DOA {

    public OrderDao() throws SQLException {
        super();
    }

    public int createOrder(OrderEntity order) throws SQLException {
        String query = "INSERT INTO orders (user_id, status, total_amount, paid_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getStatus());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setDouble(4, order.getPaidAmount());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean updateOrder(OrderEntity order) throws SQLException {
        String query = "UPDATE orders SET status = ?, total_amount = ?, paid_amount = ?, updated_at = CURRENT_TIMESTAMP WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getStatus());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setDouble(3, order.getPaidAmount());
            stmt.setInt(4, order.getOrderId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteOrder(int orderId) throws SQLException {
        String query = "DELETE FROM orders WHERE order_id = ? AND status != 'DELIVERED'";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        }
    }

    public OrderEntity findOrderById(int orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderEntity order = new OrderEntity();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaidAmount(rs.getDouble("paid_amount"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return order;
                }
            }
        }
        return null;
    }

    public List<OrderEntity> findOrdersByUserId(int userId) throws SQLException {
        List<OrderEntity> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderEntity order = new OrderEntity();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setPaidAmount(rs.getDouble("paid_amount"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }
}
