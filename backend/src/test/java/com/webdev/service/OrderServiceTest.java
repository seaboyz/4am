package com.webdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.webdev.utils.TestUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// integration test for OrderService

public class OrderServiceTest {

    private static SessionFactory sessionFactory;
    private Session session;
    // private OrderService orderService;
    // private OrderDao orderDao;
    // private ProductDao productDao;
    // private CustomerDao customerDao;
    // private AddressDao addressDao;
    // private AddressService addressService;
    // private ProductService productService;
    // private CustomerService customerService;

    @BeforeAll
    public static void init() throws Exception {
        sessionFactory = TestUtil.getSessionFactory();
    }

    @BeforeEach
    public void setUp() throws Exception {
        session = sessionFactory.openSession();
        // orderDao = new OrderDao(sessionFactory);
        // productDao = new ProductDao(sessionFactory);
        // customerDao = new CustomerDao(sessionFactory);
        // addressDao = new AddressDao(sessionFactory);
        // productService = new ProductService(productDao);
        // customerService = new CustomerService(customerDao);
        // addressService = new AddressService(addressDao, customerService);
        // orderService = new OrderService(orderDao, customerService, productService,
        // addressService);

    }

    // @AfterEach
    // public void tearDown() {
    // session.close();
    // // clear up the database
    // sessionFactory.getCurrentSession().createQuery("delete from
    // Customer").executeUpdate();
    // sessionFactory.getCurrentSession().createQuery("delete from
    // Order").executeUpdate();
    // sessionFactory.getCurrentSession().createQuery("delete from
    // OrderItem").executeUpdate();
    // sessionFactory.getCurrentSession().createQuery("delete from
    // Product").executeUpdate();
    // }

    // @AfterAll
    // public static void destroy() {
    // sessionFactory.close();
    // }

    @Test
    public void testPlaceAOrder() {
        SessionFactory sessionFactory = TestUtil.getSessionFactory();
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

        customer = customerService.getCustomerById(customerId);

        assertEquals(1, orderId);


        assertEquals(1,customer.getAddresses().size());

    }

}

// // unit test for OrderService
// @ExtendWith(MockitoExtension.class)
// public class OrderServiceTest {
// static SessionFactory sessionFactory;
// private Session session;
// OrderService orderService;

// @Mock
// OrderDao orderDao;

// @Mock
// CustomerService customerService;

// @Mock
// ProductService productService;

// @Mock
// AddressService addressService;

// @BeforeAll
// public static void init() {
// sessionFactory = TestUtil.getSessionFactory();
// }

// @BeforeEach
// public void setUp() {
// orderService = new OrderService(orderDao, customerService, productService,
// addressService);
// session = sessionFactory.openSession();
// }

// @AfterEach
// public void tearDown() {
// session.close();
// // clear up the database
// sessionFactory.getCurrentSession().createQuery("delete from
// Customer").executeUpdate();
// sessionFactory.getCurrentSession().createQuery("delete from
// Order").executeUpdate();
// sessionFactory.getCurrentSession().createQuery("delete from
// OrderItem").executeUpdate();
// sessionFactory.getCurrentSession().createQuery("delete from
// Product").executeUpdate();
// }

// @AfterAll
// public static void destroy() {
// sessionFactory.close();
// }

// // todo: not done
// @Disabled
// @Test
// void testPlaceAOrder() {
// Product product1 = new Product(
// "Fjallraven - Foldsack No. 1Backpack, Fits 15 Laptops",
// "Your perfect pack for everyday use and walks in the forest. Stash your
// laptop (up to 15 inches) in the padded sleeve, your everyday",
// 109.95,
// "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
// "men's clothing");

// Product product2 = new Product(
// "Mens Casual Premium Slim Fit T-Shirts",
// "Slim-fitting style, contrast raglan long sleeve, three-button henley
// placket",
// 22.3,
// "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
// "men's clothing");

// ShippingAddress shippingAddress = new ShippingAddress(
// "david",
// "morrison",
// "86 Frances Ct",
// "",
// "Cullman",
// "TN",
// "38301",
// "USA");

// Customer customer = new Customer("johnd", "john@gmail.com", "m38rmF",
// "1-570-236-7033");

// Integer customerId = customer.getId();

// OrderItem orderItem1 = new OrderItem(product1, 1);
// OrderItem orderItem2 = new OrderItem(product2, 2);

// Set<OrderItem> orderItems = new HashSet<OrderItem>();
// orderItems.add(orderItem1);
// orderItems.add(orderItem2);
// Order order = new Order(customer, shippingAddress, orderItems);

// session.beginTransaction();
// session.save(customer);
// session.save(product1);
// session.save(product2);
// // session.save(order);
// session.getTransaction().commit();
// session.close();

// when(customerService.getCustomerById(customerId)).thenReturn(customer);
// when(productService.getProductById(product1.getId())).thenReturn(product1);
// when(productService.getProductById(product2.getId())).thenReturn(product2);

// // todo: mock orderdao.saveOrder(order)
// // doAnswer(answer -> {
// // session = sessionFactory.openSession();
// // session.beginTransaction();
// // Integer id = (Integer) session.save(order);
// // session.getTransaction().commit();
// // session.close();
// // return id;
// // }).when(orderDao).add(order);
// doNothing().when(orderDao).add(order);
// // todo: mock addressService.saveAddress(shippingAddress)
// doNothing().when(addressService).addAddressToCustomer(customerId,
// shippingAddress);

// // place a order
// HashMap<Integer, Integer> orderList = new HashMap<Integer, Integer>();
// orderList.put(product1.getId(), 1);
// orderList.put(product2.getId(), 2);

// Integer orderId = orderService.placeAOrder(customerId, shippingAddress,
// orderList);

// // assert
// assert orderId == 1;

// }

// }
