package com.example.Entities;

import java.sql.Timestamp;

public class TransactionEntity {
    private int transactionId;
    private int orderId;
    private double amount;
    private Timestamp transactionDate;

    // Constructor, Getters, and Setters
    public TransactionEntity() {}

    public TransactionEntity(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }
}