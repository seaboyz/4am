package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.google.gson.Gson;
import com.webdev.model.Product;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDaoTest {
    @Autowired
    static EntityManager entityManager;

    @Autowired
    static ProductDao productDao;

    Session currentSession;

    Gson gson = new Gson();

    Product product;

    Product newProduct;

    @BeforeAll
    public static void init() {

    }

    @BeforeEach
    public void setUp() {
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

        // pre-inserted data and make sure the currentSession is closed before each test
        currentSession = entityManager.unwrap(Session.class);
        currentSession.save(product);
        currentSession.beginTransaction();
        currentSession.save(product);
        currentSession.getTransaction().commit();

    }

    @AfterEach
    public void closeSession() {
        currentSession.close();
    }



    @Test
    void testAdd() {

        productDao.add(newProduct);


        Product productFromDb = currentSession.get(Product.class, newProduct.getId());
        // check if the product was added
        // use json to compare the product
        assertEquals(
                gson.toJson(newProduct, Product.class),
                gson.toJson(productFromDb, Product.class));
        // check how many products are in the database, should be 2
        assertEquals(
                2,
                currentSession.createQuery("from Product").list().size());

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
        currentSession.beginTransaction();
        currentSession.save(newProduct);
        currentSession.getTransaction().commit();
        currentSession.close();

        // get all products
        assertEquals(
                2,
                productDao.getAll().size());

    }

    @Test
    void testDelete() {

        Integer id = product.getId();

        productDao.delete(id);

        // open a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        Product productFromDb = currentSession.get(Product.class, id);
        assertEquals(
                null,
                productFromDb);
        assertEquals(
                0,
                currentSession.createQuery("from Product").list().size());

    }

    @Test
    void testDelete2() {
        productDao.delete(product);
        // open a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        Product productFromDb = currentSession.get(Product.class, product.getId());
        assertEquals(
                null,
                productFromDb);
        assertEquals(
                0,
                currentSession.createQuery("from Product").list().size());
    }

    @Test
    void testUpdate() {
        product.setPrice(99.99);

        productDao.update(product);

        // open a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        Product productFromDb = currentSession.get(Product.class, product.getId());
        assertEquals(
                gson.toJson(product, Product.class),
                gson.toJson(productFromDb, Product.class));
    }
}
