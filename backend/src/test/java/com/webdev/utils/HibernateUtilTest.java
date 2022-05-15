package com.webdev.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HibernateUtilTest {
    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setUpBeforeAllTests() throws Exception {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory created.");
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
    }  

    @AfterEach   
    public void closeSession() {
        if (session != null)
            session.close();
        System.out.println("Session closed\n");

        // remove all customers from the database
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Customer").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory destoryed.");
        }
    }

    @Test
    public void testCreate() {
        session.beginTransaction();

        Customer customer1 = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");

        Integer id = (Integer) session.save(customer1);

        session.getTransaction().commit();

        assertTrue(id > 0);

    }

    @Test
    public void testGet() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
        // then customer becomes detached

        // start a new session
        // get customer from database
        session = sessionFactory.openSession();
        session.beginTransaction();
        Customer getCustomer = session.get(Customer.class, customer.getId());

        assertEquals("username1", getCustomer.getUsername());
    }

    @Test
    public void testUpdate() {

        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
        // then customer becomes detached

        // start a new session
        // update customer
        session = sessionFactory.openSession();
        session.beginTransaction();
        customer.setUsername("username2");
        session.update(customer);
        // then customer becomes persistent
        session.getTransaction().commit();
        session.close();

        // start a new session
        // get customer from database
        session = sessionFactory.openSession();
        session.beginTransaction();
        Customer updatCustomer = session.get(Customer.class, customer.getId());

        assertEquals("username2", updatCustomer.getUsername());

    }

  

    @Test
    public void testList() {
        Customer customer1 = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer1);
        session.getTransaction().commit();
        session.close();
        // then customer becomes detached

        // start a new session
        session = sessionFactory.openSession();
        Customer customer2 = new Customer(
                "username2",
                "email2",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer2);
        session.getTransaction().commit();
        session.close();
        // then customer becomes detached

        // start a new session
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Customer> query = session.createQuery("from Customer", Customer.class);
        List<Customer> customers = query.getResultList();
        assertEquals(2, customers.size());

    }

    @Test
    public void testDelete() {
        Customer customer1 = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer1);
        session.getTransaction().commit();
        session.close();
        // then customer becomes detached

        // start a new session
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(customer1);
        session.getTransaction().commit();
        session.close();

        // start a new session
        session = sessionFactory.openSession();
        session.beginTransaction();

        Customer deletedCustomer = session.get(Customer.class, customer1.getId());

        assertEquals(null, deletedCustomer);
    }
}
