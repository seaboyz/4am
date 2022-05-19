### user story
#### Place a order
when customer buy a product, frontend will create a cart
then customer can add product to cart, then the frontend will create a cart item with product id and quantity
before customer checkout, the frontend will never talk to backend
all the cart_item(product_id, quantity) will be saved in the frontend local storage/session storage/cookie storage

* when customer checkout, he/she has to provide address and payment information. * frontend will get the cart_item(product_id and quantity) from local storage/session storage/cookie storage * frontend will create an json object with customer_id, product_id, quantity, shipping_address and payment_info, send it to backend
* when backend receive the json object, it will create an order with customer_id, shipping_address and payment_info * first backend will create a empty order with order_id and customer_id and pending status * then backend will create a order_item with order_id, product_id, quantity,subtotal and save it in the database * then backend will update the total in the order table based on the subtotal of the order_item * then after backend verify the payment information, backend will update the status of the order to paid * backend will send a json object with order_id, customer_id, shipping_address, payment_info, status, total, order_items to the frontend
* after frontend receive the json object with the order infomation from the backend, it will clear the cart_item in the local storage/session storage/cookie storage * show the customer order received message
