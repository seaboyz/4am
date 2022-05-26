package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.webdev.model.Customer;
import com.webdev.model.Order;
import com.webdev.model.OrderItem;
import com.webdev.model.Product;
import com.webdev.model.ShippingAddress;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    static OrderDao orderDao;

    Session currentSession;
    Customer customer;
    ShippingAddress shippingAddress;
    Product product;
    OrderItem orderItem;
    Order order;

    @BeforeEach
    void init() {

        customer = new Customer(
                "johnd",
                "john@gmail.com",
                "m38rmF$",
                "123456789");
        shippingAddress = new ShippingAddress(
                "john",
                "doe",
                "7682 new road",
                "",
                "kilcoole",
                "CA",
                "90210",
                "USA");
        product = new Product(
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                109.95,
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "men's clothing");

        orderItem = new OrderItem(product, 1);

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        order = new Order(customer, shippingAddress, orderItemList);
        currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        currentSession.save(product);
        currentSession.save(customer);
        currentSession.save(order);
        // the orderItemList is saved automatically, because the cascade type is ALL
        currentSession.getTransaction().commit();

    }

    @AfterEach
    void tearDown() {
        if (currentSession != null) {
            currentSession.close();
        }
        // clean up the database
        currentSession.beginTransaction();
        currentSession.createQuery("delete from OrderItem").executeUpdate();
        currentSession.createQuery("delete from Order").executeUpdate();
        currentSession.createQuery("delete from Customer").executeUpdate();
        currentSession.createQuery("delete from Product").executeUpdate();
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    @Test
    void testAdd() {
        Customer customer = new Customer(
                "mor_2314",
                "morrison@gmail.com",
                "83r5^_",
                "123456789");
        ShippingAddress shippingAddress = new ShippingAddress(
                "david",
                "morrison",
                "86 Frances Ct",
                "",
                "Cullman",
                "TN",
                "38301",
                "USA");
        Product product = new Product(
                "Mens Casual Premium Slim Fit T-Shirts",
                "Slim-fitting style, contrast raglan long sleeve, three-button henley placket",
                22.3,
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                "men's clothing");

        OrderItem orderItem = new OrderItem(product, 1);

        // save orderItem

        currentSession.beginTransaction();
        currentSession.save(product);
        currentSession.save(orderItem);
        currentSession.save(customer);
        currentSession.getTransaction().commit();
        currentSession.close();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem);

        Order order = new Order(customer, shippingAddress, orderItemList);

        orderDao.add(order);

        // check if the order is saved

        currentSession.beginTransaction();
        Order savedOrder = currentSession.get(Order.class, order.getId());
        currentSession.getTransaction().commit();
        currentSession.close();

        assert savedOrder != null;

        currentSession.beginTransaction();
        // get all the orders
        Query<Order> query = currentSession.createQuery("from Order", Order.class);
        List<Order> orderList = query.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();

        assertEquals(2, orderList.size());

    }

    @Test
    void testGet() {

        Order orderFromDb = orderDao.get(order.getId());

        Gson gson = new Gson();

        assertEquals(gson.toJson(order, Order.class), gson.toJson(orderFromDb, Order.class));

    }

}
