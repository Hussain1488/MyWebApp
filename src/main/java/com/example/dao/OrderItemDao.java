package com.example.dao;

import com.example.Entities.OrderItemEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//order Item Dao class for database operations for order items table and its relations.
public class OrderItemDao extends DOA {

    public OrderItemDao() throws SQLException {
        super();
    }

    //    inserting in to order_item table
    public boolean addOrderItem(OrderItemEntity item) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPrice());
            return stmt.executeUpdate() > 0;
        }
    }


    //      Getting list of order items for an order
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

