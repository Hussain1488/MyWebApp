package com.example.Entities;

import java.sql.Timestamp;
import java.util.List;

public class OrderEntity {
    private int orderId;
    private int userId;
    private String status;
    private double totalAmount;
    private double paidAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItemEntity> orderItems;

    public OrderEntity(int orderId, int userId, String status, double totalAmount, double paidAmount, Timestamp createdAt, Timestamp updatedAt, List<OrderItemEntity> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = orderItems;
    }
    public OrderEntity() {}

    public OrderEntity(int userId, double totalAmount, String status) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }


}
