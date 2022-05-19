package com.webdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import com.webdev.model.Customer;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;

import org.hibernate.Session;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

// integration test for OrderService

@Disabled
public class OrderServiceTest {

        @Autowired
        EntityManager entityManager;

        @Autowired
        OrderService orderService;

        @Autowired
        CustomerService customerService;

        Session currentSession;

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

                Customer customer = new Customer("johnd", "john@gmail.com", "m38rmF", "1-570-236-7033");

                OrderItem orderItem1 = new OrderItem(product1, 1);
                OrderItem orderItem2 = new OrderItem(product2, 2);

                Set<OrderItem> orderItems = new HashSet<OrderItem>();
                orderItems.add(orderItem1);
                orderItems.add(orderItem2);

                currentSession = entityManager.unwrap(Session.class);
                currentSession.beginTransaction();
                currentSession.save(product1);
                currentSession.save(product2);
                currentSession.save(customer);
                Integer customerId = customer.getId();
                currentSession.getTransaction().commit();
                currentSession.close();

                System.out.println(customerId);

                // place a order

                HashMap<Integer, Integer> orderList = new HashMap<Integer, Integer>();
                orderList.put(product1.getId(), 1);
                orderList.put(product2.getId(), 2);

                Integer orderId = orderService.placeAOrder(
                                customerId,
                                shippingAddress,
                                orderList);

                customer = customerService.getCustomerById(customerId);

                assertEquals(1, orderId);

                assertEquals(1, customer.getOrders().size());

        }

}
