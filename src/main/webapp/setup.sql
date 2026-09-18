CREATE DATABASE IF NOT EXISTS grocerydb;

USE grocerydb;


DROP USER IF EXISTS
'groceryuser'@'%';


CREATE USER
'groceryuser'@'%'
IDENTIFIED BY 'grocery123';


GRANT ALL PRIVILEGES
ON grocerydb.*
TO 'groceryuser'@'%';


FLUSH PRIVILEGES;


DROP TABLE IF EXISTS order_items;

DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS users;


CREATE TABLE users (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    created_at TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP

);


CREATE TABLE products (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    category VARCHAR(50) NOT NULL,

    description VARCHAR(500),

    price DECIMAL(10,2) NOT NULL,

    stock INT NOT NULL,

    image VARCHAR(255)

);


CREATE TABLE orders (

    id INT PRIMARY KEY AUTO_INCREMENT,

    user_id INT NOT NULL,

    total DECIMAL(10,2) NOT NULL,

    status VARCHAR(50)
        DEFAULT 'PLACED',

    address VARCHAR(500),

    payment_method VARCHAR(50),

    order_date TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);


CREATE TABLE order_items (

    id INT PRIMARY KEY AUTO_INCREMENT,

    order_id INT NOT NULL,

    product_id INT NOT NULL,

    quantity INT NOT NULL,

    price DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_item_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id),

    CONSTRAINT fk_item_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)

);


INSERT INTO users
(
    name,
    email,
    password
)
VALUES
(
    'Admin',
    'admin@groceryhub.com',
    'admin123'
),
(
    'Demo User',
    'user@groceryhub.com',
    'user123'
);


INSERT INTO products
(
    name,
    category,
    description,
    price,
    stock,
    image
)
VALUES

(
    'Fresh Tomato',
    'Vegetables',
    'Fresh farm tomatoes',
    40.00,
    100,
    'tomato.jpg'
),

(
    'Potato',
    'Vegetables',
    'Fresh potatoes',
    35.00,
    150,
    'potato.jpg'
),

(
    'Red Apple',
    'Fruits',
    'Fresh Kashmiri apples',
    180.00,
    50,
    'apple.jpg'
),

(
    'Banana',
    'Fruits',
    'Fresh bananas',
    60.00,
    100,
    'banana.jpg'
),

(
    'Fresh Milk',
    'Dairy',
    'Full cream milk',
    60.00,
    80,
    'milk.jpg'
),

(
    'Curd',
    'Dairy',
    'Fresh natural curd',
    50.00,
    70,
    'curd.jpg'
),

(
    'Basmati Rice',
    'Staples',
    'Premium basmati rice',
    120.00,
    100,
    'rice.jpg'
),

(
    'Wheat Flour',
    'Staples',
    'Whole wheat flour',
    55.00,
    100,
    'flour.jpg'
),

(
    'Orange Juice',
    'Beverages',
    'Fresh orange juice',
    90.00,
    60,
    'juice.jpg'
),

(
    'Potato Chips',
    'Snacks',
    'Crispy potato chips',
    30.00,
    100,
    'chips.jpg'
);


SELECT
    'Database setup completed successfully'
    AS message;


SELECT
    user,
    host
FROM
    mysql.user
WHERE
    user = 'groceryuser';