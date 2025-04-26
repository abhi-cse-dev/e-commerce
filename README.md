# 🛒 E-commerce System

## ✅ Project Overview

This project is an E-commerce platform built using Java, JDBC, and Swing. It enables users to interact with the system via both a Command-Line Interface (CLI) and a Graphical User Interface (GUI). The application manages users, products, carts, and orders, providing a hands-on demonstration of core e-commerce functionality and database integration.

## 💡 Features

- 👤 User Registration & Login
- 🛍️ View Product Catalog
- ➕ Add to Cart / ➖ Remove from Cart
- 📦 Place Order
- 📜 View Order History
- 🖥️ GUI Interface with Java Swing

## 🖥️ GUI Highlights (Swing)

- Login and Registration Windows
- Product List Display in Tables
- Cart Management via Buttons and Dialogs
- Order Placement and Confirmation Popups
- Clean, User-Friendly Layout

## 🛠️ Technologies Used

- Java (Core)
- Swing (Java GUI)
- JDBC (Java Database Connectivity)
- MySQL (Relational Database)

## 🗄️ Database Schema (MySQL)

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    price DOUBLE,
    stock INT
);

CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
