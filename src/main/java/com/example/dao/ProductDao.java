package com.example.dao;

import com.example.Entities.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//product Dao class for database operations for product table and its relative tables.
public class ProductDao extends DOA {


    public ProductDao() throws SQLException {
        super();
    }

    // Method to insert a new product into the database
    public boolean createProduct(ProductEntity product) throws SQLException {
        String query = "INSERT INTO products (title, price, discount, status, description, stock, updated_at, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProductTitle());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getDiscount());
            stmt.setString(4, product.getStatus().toString());
            stmt.setString(5, product.getDescription());
            stmt.setInt(6, product.getStock());
            stmt.setTimestamp(7, product.getUpdatedOn());
            stmt.setTimestamp(8, product.getCreatedOn());
            return stmt.executeUpdate() > 0;
        } 
    }

    // Method to retrieve all products from the database
    public List<ProductEntity> getAllProducts() throws SQLException {
        List<ProductEntity> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ProductEntity product = new ProductEntity(
                        rs.getString("title"),
                        rs.getDouble("price"),
                        rs.getInt("discount"),
                        rs.getString("description"),
                        rs.getInt("stock")
                );

                product.setProductId(rs.getInt("product_id"));
                product.setCreatedOn(rs.getTimestamp("created_at"));
                product.setUpdatedOn(rs.getTimestamp("updated_at"));
                products.add(product);
            }
        }
        return products;
    }

    // Method to update a product in the database
    public boolean updateProduct(ProductEntity product) throws SQLException {
        String query = "UPDATE products SET title = ?, price = ?, discount = ?, status = ?, description = ?, stock = ?, updated_at = ? WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProductTitle());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getDiscount());
            stmt.setString(4, product.getStatus().toString());
            stmt.setString(5, product.getDescription());
            stmt.setInt(6, product.getStock());
            stmt.setTimestamp(7, product.getUpdatedOn());
            stmt.setInt(8, product.getProductId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Method to delete a product from the database
    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Method to find a product by ID
    public ProductEntity findProductById(int productId) throws SQLException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ProductEntity product = new ProductEntity(
                            rs.getString("title"),
                            rs.getInt("price"),
                            rs.getInt("discount"),
                            rs.getString("description"),
                            rs.getInt("stock")
                    );
                    product.setProductId(rs.getInt("product_id"));
                    product.setStock(rs.getInt("stock"));
                    product.setCreatedOn(rs.getTimestamp("created_at"));
                    product.setUpdatedOn(rs.getTimestamp("updated_at"));
                    return product;
                }
            }
        }
        return null;
    }

}