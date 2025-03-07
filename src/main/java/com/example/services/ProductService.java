package com.example.services;

import com.example.Entities.ProductEntity;
import com.example.dao.ProductDao;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductDao productDao;

    public ProductService() throws SQLException {
        this.productDao = new ProductDao();
    }

    // Method to create a new product
    public boolean createProduct(ProductEntity product) throws SQLException {
        return productDao.createProduct(product);
    }

    // Method to retrieve all products
    public List<ProductEntity> getAllProducts() throws SQLException {
        return productDao.getAllProducts();
    }

    // Method to update a product
    public boolean updateProduct(ProductEntity product) throws SQLException {
        return productDao.updateProduct(product);
    }

    // Method to delete a product
    public boolean deleteProduct(int productId) throws SQLException {
        return productDao.deleteProduct(productId);
    }

    // Method to find a product by ID
    public ProductEntity findProductById(int productId) throws SQLException {
        return productDao.findProductById(productId);
    }
}