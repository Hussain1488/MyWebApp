-- Clear existing data
DELETE FROM transactions;
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM users;

-- Insert 10 users (1 admin and 9 customers)
INSERT INTO users (user_name, first_name, last_name, email, password, role, created_at, updated_at)
VALUES
    ('admin', 'Admin', 'User', 'admin@example.com', SHA2('admin123', 256), 'admin', NOW(), NOW()),
    ('customer1', 'Customer', 'User1', 'customer1@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer2', 'Customer', 'User2', 'customer2@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer3', 'Customer', 'User3', 'customer3@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer4', 'Customer', 'User4', 'customer4@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer5', 'Customer', 'User5', 'customer5@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer6', 'Customer', 'User6', 'customer6@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer7', 'Customer', 'User7', 'customer7@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer8', 'Customer', 'User8', 'customer8@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW()),
    ('customer9', 'Customer', 'User9', 'customer9@example.com', SHA2('customer123', 256), 'customer', NOW(), NOW());

-- Insert 20 products with prices divisible by 5
INSERT INTO products (title, price, discount, status, description, stock, created_at, updated_at)
VALUES
    ('Product 1', 10.00, 10, 'IN_STOCK', 'Description for Product 1', 50, NOW(), NOW()),
    ('Product 2', 15.00, 15, 'IN_STOCK', 'Description for Product 2', 40, NOW(), NOW()),
    ('Product 3', 20.00, 20, 'IN_STOCK', 'Description for Product 3', 30, NOW(), NOW()),
    ('Product 4', 25.00, 25, 'IN_STOCK', 'Description for Product 4', 20, NOW(), NOW()),
    ('Product 5', 30.00, 30, 'IN_STOCK', 'Description for Product 5', 10, NOW(), NOW()),
    ('Product 6', 35.00, 35, 'OUT_STOCK', 'Description for Product 6', 0, NOW(), NOW()),
    ('Product 7', 40.00, 40, 'IN_STOCK', 'Description for Product 7', 60, NOW(), NOW()),
    ('Product 8', 45.00, 45, 'IN_STOCK', 'Description for Product 8', 70, NOW(), NOW()),
    ('Product 9', 50.00, 50, 'IN_STOCK', 'Description for Product 9', 80, NOW(), NOW()),
    ('Product 10', 55.00, 55, 'OUT_STOCK', 'Description for Product 10', 0, NOW(), NOW()),
    ('Product 11', 60.00, 10, 'IN_STOCK', 'Description for Product 11', 90, NOW(), NOW()),
    ('Product 12', 65.00, 20, 'IN_STOCK', 'Description for Product 12', 100, NOW(), NOW()),
    ('Product 13', 70.00, 10, 'IN_STOCK', 'Description for Product 13', 110, NOW(), NOW()),
    ('Product 14', 75.00, 15, 'OUT_STOCK', 'Description for Product 14', 0, NOW(), NOW()),
    ('Product 15', 80.00, 20, 'IN_STOCK', 'Description for Product 15', 120, NOW(), NOW()),
    ('Product 16', 85.00, 20, 'IN_STOCK', 'Description for Product 16', 130, NOW(), NOW()),
    ('Product 17', 90.00, 25, 'IN_STOCK', 'Description for Product 17', 140, NOW(), NOW()),
    ('Product 18', 95.00, 10, 'OUT_STOCK', 'Description for Product 18', 0, NOW(), NOW()),
    ('Product 19', 100.00, 5, 'IN_STOCK', 'Description for Product 19', 150, NOW(), NOW()),
    ('Product 20', 105.00, 35, 'IN_STOCK', 'Description for Product 20', 160, NOW(), NOW());

-- Insert orders for each user
INSERT INTO orders (user_id, status, total_amount, paid_amount, created_at, updated_at)
VALUES
    (1, 'PENDING', 0, 0, NOW(), NOW()),
    (2, 'PAID', 0, 0, NOW(), NOW()),
    (3, 'DELIVERED', 0, 0, NOW(), NOW()),
    (4, 'CANCELLED', 0, 0, NOW(), NOW()),
    (5, 'PENDING', 0, 0, NOW(), NOW()),
    (6, 'PAID', 0, 0, NOW(), NOW()),
    (7, 'DELIVERED', 0, 0, NOW(), NOW()),
    (8, 'CANCELLED', 0, 0, NOW(), NOW()),
    (9, 'PENDING', 0, 0, NOW(), NOW()),
    (10, 'PAID', 0, 0, NOW(), NOW());

-- Insert order items for each order (2 to 5 products per order)
INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES
    (1, 1, 2, 10.00), (1, 2, 1, 15.00),
    (2, 3, 3, 20.00), (2, 4, 2, 25.00),
    (3, 5, 4, 30.00), (3, 6, 1, 35.00),
    (4, 7, 2, 40.00), (4, 8, 3, 45.00),
    (5, 9, 5, 50.00), (5, 10, 2, 55.00),
    (6, 11, 3, 60.00), (6, 12, 4, 65.00),
    (7, 13, 2, 70.00), (7, 14, 1, 75.00),
    (8, 15, 3, 80.00), (8, 16, 2, 85.00),
    (9, 17, 4, 90.00), (9, 18, 1, 95.00),
    (10, 19, 2, 100.00), (10, 20, 3, 105.00);

-- Update total_amount for orders
UPDATE orders 
SET 
    total_amount = (SELECT 
            SUM(quantity * price)
        FROM
            order_items
        WHERE
            order_items.order_id = orders.order_id);

-- Insert transactions for orders
INSERT INTO transactions (order_id, amount, transaction_date)
VALUES
    (1, 10.00, NOW()), -- PENDING
    (2, 110.00, NOW()), -- PAID
    (3, 155.00, NOW()), -- DELIVERED
    (4, 215.00, NOW()), -- CANCELLED
    (5, 360.00, NOW()), -- PENDING
    (6, 440.00, NOW()), -- PAID
    (7, 215.00, NOW()), -- DELIVERED
    (9, 300.00, NOW()), -- PENDING
    (10, 515.00, NOW());-- PAID

UPDATE orders 
SET 
    paid_amount = (SELECT 
            COALESCE(SUM(amount), 0)
        FROM
            transactions
        WHERE
            transactions.order_id = orders.order_id);
