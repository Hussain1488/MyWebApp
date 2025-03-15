package com.example.dao;

import com.example.Entities.TransactionEntity;
import java.sql.*;

public class TransactionDao extends DOA {

    public TransactionDao() throws SQLException {
        super();
    }

    // Add a new transaction
    public boolean addTransaction(TransactionEntity transaction) throws SQLException {
        // Start a transaction to ensure atomicity
        connection.setAutoCommit(false);

        try {
            // Step 1: Insert the new transaction
            String insertQuery = "INSERT INTO transactions (order_id, amount) VALUES (?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, transaction.getOrderId());
                insertStmt.setDouble(2, transaction.getAmount());
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted <= 0) {
                    connection.rollback(); // Rollback if insertion fails
                    return false;
                }
            }

            // Step 2: Update the paid_amount in the orders table
            String updateQuery = "UPDATE orders SET paid_amount = paid_amount + ? WHERE order_id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setDouble(1, transaction.getAmount());
                updateStmt.setInt(2, transaction.getOrderId());
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated <= 0) {
                    connection.rollback(); // Rollback if update fails
                    return false;
                }
            }

            String updateStatusQuery = "UPDATE orders SET status = 'PAID'"+
            "WHERE order_id = ? AND status NOT IN ('DELIVERED', 'CANCELED') AND paid_amount >= total_amount";

            try (PreparedStatement updateStatusStmt = connection.prepareStatement(updateStatusQuery)) {
                updateStatusStmt.setInt(1, transaction.getOrderId());
                updateStatusStmt.executeUpdate(); // Update status if conditions are met
            }

            // Commit the transaction if both operations succeed
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback(); // Rollback on any error
            throw e;
        } finally {
            connection.setAutoCommit(true); // Reset auto-commit mode
        }
    }

    // Get the total amount paid for an order
    public double getTotalPaid(int orderId) throws SQLException {
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

    public double getTotalPaidAmount(int orderId) throws SQLException {
        String query = "SELECT SUM(amount) AS total FROM transactions WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }

        }
        return 0;
    }
}