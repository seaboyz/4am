DROP DATABASE IF EXISTS qianggao_p2;
CREATE DATABASE qianggao_p2;

SELECT * FROM customers;

DELETE FROM addresses;
DELETE FROM cart_items;
DELETE FROM customers;
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;

INSERT INTO customers (email, password, phone_number, username)
VALUES ('john@gmail.com', '1-570-236-7033',  'm38rmF$', 'johnd');




