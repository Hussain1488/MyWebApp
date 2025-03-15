CREATE DATABASE my_app;

use my_app;

SELECT 
    *
FROM
    users;

DELETE FROM users WHERE user_id > 0; -- Assuming 'id' is a primary key




--  ****>  Creating Schemas <****  --


--    --> Creating users table <-- 
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(70) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) DEFAULT NULL,
    role ENUM('customer', 'admin') DEFAULT 'customer',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL    
);
-- DROP TABLE users;

SELECT 
    *
FROM
    users;

--    --> Creating order table <-- 
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    status ENUM('PENDING', 'PAID', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    total_amount DECIMAL(10, 2),
    paid_amount DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
select * from orders;
select * from transactions;
DROP TABLE orders;


--    --> Creating products table <-- 
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    price DECIMAL(10,2) DEFAULT 0.00,
    discount TINYINT NOT NULL CHECK (discount >= 0 AND discount <= 100),
    status ENUM('IN_STOCK', 'OUT_STOCK') DEFAULT 'OUT_STOCK',
    description TEXT,
    stock INT DEFAULT 0,
    updated_at DATETIME,
    created_at DATETIME
);
DROP TABLE products ;


--    --> Creating order items table <-- 
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
select * from orders;
select * from order_items;
SELECT * FROM products;


drop table order_items;


--    --> Creating transactions table <-- 

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
-- DROP TABLE transactions;


DROP TABLE transactions;
DROP TABLE order_items;
DROP TABLE orders;
DROP TABLE users;

use my_app;





