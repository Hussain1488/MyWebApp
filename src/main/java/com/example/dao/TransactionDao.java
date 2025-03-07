package com.example.dao;

import com.example.Entities.TransactionEntity;
import java.sql.*;

public class TransactionDao extends DOA {

    public TransactionDao() throws SQLException {
        super();
    }

    public boolean addTransaction(TransactionEntity transaction) throws SQLException {
        String query = "INSERT INTO transactions (order_id, amount) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, transaction.getOrderId());
            stmt.setDouble(2, transaction.getAmount());
            return stmt.executeUpdate() > 0;
        }
    }

    public double getTotalPaidAmount(int orderId) throws SQLException {
        String query = "SELECT SUM(amount) AS total_paid FROM transactions WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_paid");
                }
            }
        }
        return 0.0;
    }
}