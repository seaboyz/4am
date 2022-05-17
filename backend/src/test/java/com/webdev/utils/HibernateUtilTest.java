package com.webdev.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class HibernateUtilTest {
    @Autowired
    EntityManager entityManager;
    private Session session;

    @BeforeEach
    public void setUp() throws Exception {
        session = entityManager.unwrap(Session.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (session != null)
            session.close();

        // remove all customers from the database
        session = entityManager.unwrap(Session.class);
        session.beginTransaction();
        session.createQuery("delete from Customer").executeUpdate();
        session.getTransaction().commit();
        session.close();
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
        session = entityManager.unwrap(Session.class);
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
        session = entityManager.unwrap(Session.class);
        session.beginTransaction();
        customer.setUsername("username2");
        session.update(customer);
        // then customer becomes persistent
        session.getTransaction().commit();
        session.close();

        // start a new session
        // get customer from database
        session = entityManager.unwrap(Session.class);
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
        session = entityManager.unwrap(Session.class);
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
        session = entityManager.unwrap(Session.class);
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
        session = entityManager.unwrap(Session.class);
        session.beginTransaction();
        session.delete(customer1);
        session.getTransaction().commit();
        session.close();

        // start a new session
        session = entityManager.unwrap(Session.class);
        session.beginTransaction();

        Customer deletedCustomer = session.get(Customer.class, customer1.getId());

        assertEquals(null, deletedCustomer);
    }
}
