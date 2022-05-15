package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.webdev.model.Product;
import com.webdev.utils.TestUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDaoTest {
    private static SessionFactory sessionFactory;
    private Session session;
    private static ProductDao productDao;
    private Product product;

    @BeforeAll
    public static void setUpBeforeAllTests() {
        sessionFactory = TestUtil.getSessionFactory();
        System.out.println("SessionFactory created.");
        productDao = new ProductDao(sessionFactory);

    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        product = new Product(
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                109.95,
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "men's clothing");
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();

    }

    @AfterEach
    public void closeSession() {
        if (session != null)
            session.close();

        // remove all customers from the database
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Product").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    void testAdd() {
        Product newProduct = new Product(
                "Mens Casual Premium Slim Fit T-Shirts",
                "Slim-fitting style, contrast raglan long sleeve, three-button henley placket",
                22.3,
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                "men's clothing");

        productDao.add(newProduct);

        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, newProduct.getId());
        assertEquals(newProduct.toString(), productFromDb.toString());

    }

    @Test
    void testGet() {

        Integer id = product.getId();

        Optional<Product> productFromDb = productDao.get(id);

        assertEquals(product.toString(), productFromDb.get().toString());

    }

    @Test
    void testGetAll() {

        Product newProduct = new Product(
            "Mens Casual Premium Slim Fit T-Shirts",
            "Slim-fitting style, contrast raglan long sleeve, three-button henley placket",
            22.3,
            "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            "men's clothing");
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(newProduct);
        session.getTransaction().commit();
        session.close();

        assertEquals(2, productDao.getAll().size());

    }

    @Test
    void testDelete() {
            
            Integer id = product.getId();
    
            productDao.delete(id);
    
            // open a new session
            session = sessionFactory.openSession();
            Product productFromDb = session.get(Product.class, id);
            assertEquals(null, productFromDb);
    

    }

    @Test
    void testDelete2() {
        productDao.delete(product);
        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, product.getId());
        assertEquals(null, productFromDb);
    }

    @Test
    void testUpdate() {
        product.setPrice(99.99);

        productDao.update(product);

        // open a new session
        session = sessionFactory.openSession();
        Product productFromDb = session.get(Product.class, product.getId());
        assertEquals(product.getPrice(), productFromDb.getPrice());
    }
}
