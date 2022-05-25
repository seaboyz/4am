### ERD - Entity-Relationship Diagram
<!-- <iframe
  src="https://dbdiagram.io/d/62733e857f945876b6bb6479"
  style="width:100%; height:100%;"
></iframe> -->
![](/backend/images/database/Screen%20Shot%202022-05-15%20at%204.56.54%20AM.png)

## NOTE
Add `hibernate.cfg.xml` to `/backend/src/main/resources`
[A Note About P2](https://github.com/220328-Java-Full-Stack-AWS/Curriculum-Notes/blob/main/P2.md)
![](./images/database/Screen%20Shot%202022-05-04%20at%209.14.08%20PM.png)

### Basic Design Principles
* build your application code and business logic into your database rather than into the client Web page.
* This will make your application much more portable and will allow your solution to scale upwards
* Customers Table - holds customer information like address, shipping address, billing address, etc.
* Products Table - holds product information like product name, description, size, color, unit price, etc.
* Orders Table - holds information on when an order was placed including Customer ID, date of order, order shipping date, etc.
* Order Details Table - holds information on each product ordered on one order (since typically you can purchase multiple items on the same order) including the product ordered, quantity, unit price, any discounts, etc.

### Naming Conventsions
* pay attention with reserved database keywords.
* use underscore for merged words `first_name`



#### Entities
##### User
##### ShippingAddress

### customer frontend input choice
![](./images/database/Screen%20Shot%202022-05-07%20at%2011.25.11%20AM.png)
#### ✅  instead let customer type , only radio or dropdown options are allowed
A much better approach is to limit the choices the customer can make to only those that actually exit. This constraint means that the data entered is always valid. Something that is essential in a big E-commerce app.

### order table
* Since a customer may order multiple items at one time
* the actual product information for each order (quantity, size color, ProductID, etc.) are stored in a separate OrderDetails Table. 
* The two Tables are linked by the OrderID 
* The Orders Table has a One-to-Many Relationship to the OrderDetails Table (one Order can have many OrderDetails)

#### @Transactional
![](/backend/images/spring/Screen%20Shot%202022-05-17%20at%203.36.48%20AM.png)

#### Start Class
![](images/spring/Screen%20Shot%202022-05-17%20at%208.02.43%20AM.png)
?? is this equivalent to SpringBootServletInitializer ??

| id  | email          | password       | phone_number | username |
| --- | -------------- | -------------- | ------------ | -------- |
| 2   | john@gmail.com | 1-570-236-7033 | m38rmF$      | johnd    |

### user story
#### Place a order
when customer buy a product, frontend will create a cart then customer can add product to cart, then the frontend will create a cart item with product id and quantity before customer checkout, the frontend will never talk to backend all the cart_item(product_id, quantity) will be saved in the frontend local storage/session storage/cookie storage

when customer checkout, he/she has to provide address and payment information. * frontend will get the cart_item(product_id and quantity) from local storage/session storage/cookie storage * frontend will create an json object with customer_id, product_id, quantity, shipping_address and payment_info, send it to backend
when backend receive the json object, it will create an order with customer_id, shipping_address and payment_info * first backend will create a empty order with order_id and customer_id and pending status * then backend will create a order_item with order_id, product_id, quantity,subtotal and save it in the database * then backend will update the total in the order table based on the subtotal of the order_item * then after backend verify the payment information, backend will update the status of the order to paid * backend will send a json object with order_id, customer_id, shipping_address, payment_info, status, total, order_items to the frontend
after frontend receive the json object with the order infomation from the backend, it will clear the cart_item in the local storage/session storage/cookie storage * show the customer order received message

#### login
* frontend will send a json object with email and password to backend
* backend will check if the email and password match with the database
* if match, backend will send a json object with user_id, email, password(transient), phone_number, username, jwt token to the frontend


#### HibernateConf is the same as hibernate.cfg.xml to configure the Hibernate SessionFactory without using spirng-jpa