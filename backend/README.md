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