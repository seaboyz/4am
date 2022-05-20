package com.webdev.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.webdev.dao.OrderDao;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@Mock
	private OrderDao orderDao;

	@Mock
	private CustomerService customerService;

	@Mock
	private ProductService productService;

	private OrderService orderService;

	@BeforeEach
	public void setUp() {
		orderService = new OrderService(orderDao, customerService, productService);
	}

	@Disabled
	@Test
	public void testPlaceAOrder() {

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

		Customer customer = new Customer("johnd", "john@gmail.com", "m38rmF",
				"555-555-5555");

		OrderItem orderItem1 = new OrderItem(product1, 1);
		OrderItem orderItem2 = new OrderItem(product2, 2);

		Set<OrderItem> orderItemList = new HashSet<OrderItem>();
		orderItemList.add(orderItem1);
		orderItemList.add(orderItem2);

		Order order = new Order(customer, shippingAddress, orderItemList);

		when(customerService.getCustomerById(1)).thenReturn(customer);
		when(productService.getProductById(1)).thenReturn(product1);
		when(productService.getProductById(2)).thenReturn(product2);

		when(orderDao.add(order)).thenReturn(1);

		doNothing().when(customerService).addAddressToCustomer(1, shippingAddress);

		HashMap<Integer, Integer> itemList = new HashMap<>();
		itemList.put(1, 1);
		itemList.put(2, 2);

		Integer orderId = orderService.placeAOrder(1, shippingAddress, itemList);

		assert orderId == 1;

	}

}
