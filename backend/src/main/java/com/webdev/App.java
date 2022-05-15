package com.webdev;

import java.util.HashSet;
import java.util.Set;

import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class App {
	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Customer customer = new Customer(
				"username1",
				"email1",
				"password",
				"phoneNumber");

		ShippingAddress shippingAddress = new ShippingAddress(
				"firstname",
				"lastname",
				"street",
				"street2",
				"city",
				"state",
				"zip",
				"country");

		Product product = new Product(
				"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
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

		OrderItem orderItem = new OrderItem(product, 1);
		OrderItem orderItem2 = new OrderItem(product2, 2);

		Set<OrderItem> orderItemList = new HashSet<>();
		orderItemList.add(orderItem);
		orderItemList.add(orderItem2);

		Order order = new Order(customer, shippingAddress, orderItemList);

		session.beginTransaction();
		session.save(customer);
		session.save(product);
		session.save(product2);
		session.save(order);
		// the orderItemList is saved automatically, because the cascade type is ALL
		session.getTransaction().commit();
		session.close();

	}
}