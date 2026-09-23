CREATE DATABASE IF NOT EXISTS jayan_grocery;

USE jayan_grocery;


CREATE TABLE IF NOT EXISTS users (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    created_at TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS products (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    category VARCHAR(50) NOT NULL,

    description VARCHAR(500),

    price DECIMAL(10,2) NOT NULL,

    stock INT NOT NULL DEFAULT 0,

    image VARCHAR(255)
);

INSERT INTO products
    (name, category, description, price, stock, image)
VALUES
    ('Fresh Organic Bananas', 'Fruits', 'Fresh organic bananas', 45.00, 100,
     'https://images.unsplash.com/photo-1587132137056-bfbf0166836e?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
    ('Whole Wheat Bread', 'Bakery', 'Whole wheat bread', 40.00, 100,
     'https://images.unsplash.com/photo-1586765501019-cbe3973ef8fa?q=80&w=868&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');


CREATE TABLE IF NOT EXISTS orders (

    id INT PRIMARY KEY AUTO_INCREMENT,

    user_id INT NOT NULL,

    total DECIMAL(10,2) NOT NULL,

    status VARCHAR(50)
        DEFAULT 'PLACED',

    address VARCHAR(500),

    payment_method VARCHAR(50),

    order_date TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTS order_items (

    id INT PRIMARY KEY AUTO_INCREMENT,

    order_id INT NOT NULL,

    product_id INT NOT NULL,

    quantity INT NOT NULL,

    price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES orders(id),

    FOREIGN KEY (product_id)
        REFERENCES products(id)
);