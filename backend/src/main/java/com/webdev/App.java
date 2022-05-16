package com.webdev;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.webdev.dao.AddressDao;
import com.webdev.dao.CustomerDao;
import com.webdev.dao.OrderDao;
import com.webdev.dao.ProductDao;
import com.webdev.model.Customer;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;
import com.webdev.service.AddressService;
import com.webdev.service.CustomerService;
import com.webdev.service.OrderService;
import com.webdev.service.ProductService;
import com.webdev.utils.TestUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class App {
	public static void main(String[] args) {

		SessionFactory sessionFactory = TestUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Product product1 = new Product(
				"Fjallraven - Foldsack No. 1Backpack, Fits 15 Laptops",
				"Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
				109.95,
				"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
				"men's clothing");

		Product product2 = new Product(
				"Mens Casual Premium Slim Fit T-Shirts",
				"Slim-fitting style, contrast raglan long sleeve, three-button henley placket",
				22.3,
				"https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
				"men's clothing");

		ShippingAddress shippingAddress = new ShippingAddress(
				"david",
				"morrison",
				"86 Frances Ct",
				"",
				"Cullman",
				"TN",
				"38301",
				"USA");

		Customer customer = new Customer("johnd", "john@gmail.com", "m38rmF", "1-570-236-7033");

		OrderItem orderItem1 = new OrderItem(product1, 1);
		OrderItem orderItem2 = new OrderItem(product2, 2);

		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		orderItems.add(orderItem1);
		orderItems.add(orderItem2);

		// Order order = new Order(customer, shippingAddress, orderItems);

		session.beginTransaction();
		session.save(product1);
		session.save(product2);
		session.save(customer);
		Integer customerId = customer.getId();
		session.getTransaction().commit();
		session.close();

		System.out.println(customerId);

		// place a order

		HashMap<Integer, Integer> orderList = new HashMap<Integer, Integer>();
		orderList.put(product1.getId(), 1);
		orderList.put(product2.getId(), 2);

		OrderDao orderDao = new OrderDao(sessionFactory);
		AddressDao addressDao = new AddressDao(sessionFactory);
		ProductDao productDao = new ProductDao(sessionFactory);
		CustomerDao customerDao = new CustomerDao(sessionFactory);
		ProductService productService = new ProductService(productDao);
		CustomerService customerService = new CustomerService(customerDao);
		AddressService addressService = new AddressService(addressDao, customerService);

		OrderService orderService = new OrderService(orderDao, customerService, productService, addressService);

		Integer orderId = orderService.placeAOrder(
				customerId,
				shippingAddress,
				orderList);

		System.out.println(customer.getAddresses().size());

	}
}