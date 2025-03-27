package com.example.controllers;

import com.example.Entities.ProductEntity;
import com.example.Entities.UserEntity;
import com.example.services.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//Product controller class for managing products.
public class ProductController {

    private final Scanner sc;
    private final ProductService productService;
    private final UserEntity user;

    //    Product controller constructore.
    public ProductController(Scanner sc, UserEntity user) throws SQLException {
        this.sc = sc;
        this.productService = new ProductService();
        this.user = user;
    }

    //    Admin access menu for products
    public void menu() {
        boolean exit = false;
        int option = -1;
        while (!exit) {
            System.out.println("Product Menu:\n-->(1) Create Product \n-->(2) List Products \n-->(3) Update Product" +
                    " \n-->(4) Delete Product \n-->(5) Find Product \n-->(0) Back");

            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.next();
                continue;
            }

            switch (option) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    listProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    findProduct();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting product menu.");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }

    //    creating new product by admin
    private void createProduct() {
        System.out.print("Enter product title: ");
        String title = sc.nextLine();
        System.out.print("Enter product price: ");
        double price = sc.nextDouble();
        System.out.print("Enter product discount (0-100): ");
        int discount = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.print("Enter product description: ");
        String description = sc.nextLine();
        System.out.print("Enter product stock: ");
        int stock = sc.nextInt();
        sc.nextLine(); // Consume the newline character


        ProductEntity product = new ProductEntity(title, price, discount, description, stock);
        try {
            if (productService.createProduct(product)) {
                System.out.println("Product created successfully.");
            } else {
                System.out.println("Failed to create product.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
        }
    }

    //    monitoring and listing all products by admin
    private void listProducts() {
        try {
            List<ProductEntity> products = productService.getAllProducts();
            for (ProductEntity product : products) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            System.err.println("Error listing products: " + e.getMessage());
        }
    }

    //    updating existing product by admin
    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int productId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        try {
            ProductEntity product = productService.findProductById(productId);
            if (product == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.print("Enter new product title (leave blank to keep current): ");
            String title = sc.nextLine();
            if (!title.isEmpty()) {
                product.setProductTitle(title);
            }

            System.out.print("Enter new product price (leave blank to keep current): ");
            String priceStr = sc.nextLine();
            if (!priceStr.isEmpty()) {
                product.setPrice(Double.parseDouble(priceStr));
            }

            System.out.print("Enter new product discount (0-100, leave blank to keep current): ");
            String discountStr = sc.nextLine();
            if (!discountStr.isEmpty()) {
                product.setDiscount(Integer.parseInt(discountStr));
            }

            System.out.print("Enter new product description (leave blank to keep current): ");
            String description = sc.nextLine();
            if (!description.isEmpty()) {
                product.setDescription(description);
            }

            if (productService.updateProduct(product)) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    //    deleting existing product by admin
    private void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        int productId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        try {
            if (productService.deleteProduct(productId)) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    //    find product by id
    private void findProduct() {
        System.out.print("Enter product ID to find: ");
        int productId = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        try {
            ProductEntity product = productService.findProductById(productId);
            if (product != null) {
                System.out.println(product);
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error finding product: " + e.getMessage());
        }
    }
}