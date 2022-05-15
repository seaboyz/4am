package com.webdev.dao;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.google.gson.Gson;
import com.webdev.model.Product;
import com.webdev.utils.TestUtil;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDaoTest {
    private static SessionFactory sessionFactory;
    private static ProductDao productDao;
    private static Product product;
    private static Product newProduct;
    private static Gson gson;
    private Session session;

    @BeforeAll
    public static void init() {
        gson = new Gson();
        // progratic configure hibernate session factory and session
        sessionFactory = TestUtil.getSessionFactory();
        // init productDao
        productDao = new ProductDao(sessionFactory);

        // create 2 products
        product = new Product(
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                109.95,
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "men's clothing");
        newProduct = new Product(
                "Mens Casual Premium Slim Fit T-Shirts",
                "Slim-fitting style, contrast raglan long sleeve, three-button henley placket",
                22.3,
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                "men's clothing");
    }

    @BeforeEach
    public void setUp() {
        // pre-inserted data and make sure the session is closed before each test
        session = sessionFactory.openSession();
        session.save(product);
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    @AfterEach
    public void closeSession() {
        // clear data after each test and close session
        if (session != null)
            session.close();

        // remove all customers from the database
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Product").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    public static void destory() {
        // close session factory
        sessionFactory.close();
    }

    @Test
    void testAdd() {

        productDao.add(newProduct);

        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, newProduct.getId());
        // check if the product was added
        // use json to compare the product
        assertEquals(
                gson.toJson(newProduct, Product.class),
                gson.toJson(productFromDb, Product.class));
        // check how many products are in the database, should be 2
        assertEquals(
                2,
                session.createQuery("from Product").list().size());

    }

    @Test
    void testGet() {

        Integer id = product.getId();

        Optional<Product> productFromDb = productDao.get(id);

        assertEquals(
                gson.toJson(product, Product.class),
                gson.toJson(productFromDb.get(), Product.class));

    }

    @Test
    void testGetAll() {
        // save a new product
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(newProduct);
        session.getTransaction().commit();
        session.close();

        // get all products
        assertEquals(
                2,
                productDao.getAll().size());

        

    }

    @Test
    void testDelete() {

        Integer id = product.getId();

        productDao.delete(id);

        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, id);
        assertEquals(
                null,
                productFromDb);
        assertEquals(
                0,
                session.createQuery("from Product").list().size());

    }

    @Test
    void testDelete2() {
        productDao.delete(product);
        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, product.getId());
        assertEquals(
                null,
                productFromDb);
        assertEquals(
                0,
                session.createQuery("from Product").list().size());
    }

    @Test
    void testUpdate() {
        product.setPrice(99.99);

        productDao.update(product);

        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, product.getId());
        assertEquals(
                gson.toJson(product, Product.class),
                gson.toJson(productFromDb, Product.class));
    }
}
