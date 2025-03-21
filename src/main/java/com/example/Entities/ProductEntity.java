package com.example.Entities;

import java.sql.Timestamp;

public class ProductEntity {

    // Enum for Product Status
    public enum Status {
        IN_STOCK, OUT_STOCK
    }

    private int productId;
    private String productTitle;
    private double price;
    private int discount;
    private String description;
    private int stock;
    private Status status;
    protected Timestamp updatedOn;
    protected Timestamp createdOn;

    // Constructor
    public ProductEntity(String productTitle, double price, int discount, String description, int stock) {
        this.productTitle = productTitle;
        this.price = price;
        setDiscount(discount); // Ensures validation
        this.description = description;
        this.stock = stock;
        this.status = stock > 0 ? Status.IN_STOCK : Status.OUT_STOCK;

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.createdOn = currentTimestamp;
        this.updatedOn = currentTimestamp;
    }

    public ProductEntity(){}


    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        updateTimestamp();
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        updateTimestamp();
    }

    public double  getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updateTimestamp();
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.discount = discount;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateTimestamp();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.status = stock > 0 ? Status.IN_STOCK : Status.OUT_STOCK; // Update status based on stock
        updateTimestamp();
    }

    public Status getStatus() {
        return status;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    // Method to update timestamp when changes are made
    private void updateTimestamp() {
        this.updatedOn = new Timestamp(System.currentTimeMillis());
    }

    // toString Method for Debugging
    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", price=" + price +
                ", discount=" + discount + "%" +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
