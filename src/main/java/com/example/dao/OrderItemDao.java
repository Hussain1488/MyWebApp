package com.example.dao;

import com.example.Entities.OrderItemEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao extends DOA {

    public OrderItemDao() throws SQLException {
        super();
    }

    public boolean addOrderItem(OrderItemEntity orderItem) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateOrderItem(OrderItemEntity orderItem) throws SQLException {
        String query = "UPDATE order_items SET quantity = ?, price = ? WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItem.getQuantity());
            stmt.setDouble(2, orderItem.getPrice());
            stmt.setInt(3, orderItem.getOrderItemId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteOrderItem(int orderItemId) throws SQLException {
        String query = "DELETE FROM order_items WHERE order_item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderItemId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<OrderItemEntity> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItemEntity> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItemEntity item = new OrderItemEntity();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    items.add(item);
                }
            }
        }
        return items;
    }
}

