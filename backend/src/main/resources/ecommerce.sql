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
VALUES ('john@gmail.com','m38rmF$', '1-570-236-7033', 'johnd');
INSERT INTO products (email, password, phone_number, username)
VALUES ('morrison@gmail.com','83r5^_', '1-570-236-7033', 'mor_2314');
INSERT INTO products (email, password, phone_number, username)
VALUES ('	kevin@gmail.com','kev02937@', '1-567-094-1345', 'kevinryan');

DELETE FROM customers
WHERE username = 'username';

DELETE FROM customers
WHERE id = 14;




