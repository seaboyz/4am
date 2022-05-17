package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Customer;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDaoTest {
    @Autowired
    private static EntityManager entityManager;

    @Autowired
    private static CustomerDao customerDao;

    private Session currentSession;

    @BeforeEach
    public void init() {
        currentSession = entityManager.unwrap(Session.class);
    }

    @AfterEach
    public void tear() {
        if (currentSession != null)
            currentSession.close();
    }

    @Test
    void testAdd() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");

        customerDao.add(customer);
        // currentSession is closed in userdao.add()

        currentSession.beginTransaction();
        Customer savedCustomer = currentSession.get(Customer.class, customer.getId());

        assertEquals(customer.getUsername(), savedCustomer.getUsername());
    }

    @Test
    void testGet() {
        Customer customer = new Customer(
                "username",
                "email",
                "password",
                "phoneNumber");
        currentSession.beginTransaction();
        currentSession.save(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
        // then customer becomes detached

        assertEquals(customer.getUsername(), customerDao.get(customer.getId()).get().getUsername());

    }

    @Test
    void testGetAll() {
        Customer customer1 = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        currentSession.beginTransaction();
        currentSession.save(customer1);
        currentSession.getTransaction().commit();
        currentSession.close();
        // then customer becomes detached

        // start a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        Customer customer2 = new Customer(
                "username2",
                "email2",
                "password",
                "phoneNumber");
        currentSession.beginTransaction();
        currentSession.save(customer2);
        currentSession.getTransaction().commit();
        currentSession.close();
        // then customer becomes detached

        assertEquals(2, customerDao.getAll().size());

    }

    @Test
    void testUpdate() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        currentSession.beginTransaction();
        currentSession.save(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
        // then customer becomes detached

        customer.setUsername("username2");

        customerDao.update(customer);

        // start a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Customer updatCustomer = currentSession.get(Customer.class, customer.getId());
        currentSession.getTransaction().commit();
        currentSession.close();

        assertEquals("username2", updatCustomer.getUsername());

    }

    @Test
    void testDelete() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        currentSession.beginTransaction();
        currentSession.save(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
        // then customer becomes detached

        customerDao.delete(customer);

        // start a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Optional<Customer> deletedCustomer = customerDao.get(customer.getId());
        currentSession.getTransaction().commit();
        currentSession.close();

        assertTrue(!deletedCustomer.isPresent());
    }
}
