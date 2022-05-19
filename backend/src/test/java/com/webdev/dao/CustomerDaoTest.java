package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;

import com.webdev.model.Customer;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDaoTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    CustomerDao customerDao;

    Session currentSession;

    @BeforeEach
    public void init() {
        currentSession = entityManager.unwrap(Session.class);
    }

    @AfterEach
    public void tearDown() {
        if (currentSession != null) {
            currentSession.close();
        }
    }

    @Disabled
    @Test
    void testAdd() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");

        customerDao.add(customer);

        Customer savedCustomer = currentSession.get(Customer.class, customer.getId());

        assertEquals(customer.getUsername(), savedCustomer.getUsername());
    }

    @Disabled
    @Test
    void testGet() {
        Customer customer = new Customer(
                "username",
                "email",
                "password",
                "phoneNumber");

        currentSession.save(customer);
        currentSession.close();

        assertEquals(customer.getUsername(), customerDao.get(customer.getId()).getUsername());

    }

    @Disabled
    @Test
    void testGetAll() {
        Customer customer1 = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        Customer customer2 = new Customer(
                "username2",
                "email2",
                "password",
                "phoneNumber");

        currentSession.save(customer1);
        currentSession.save(customer2);
        currentSession.close();

        assertEquals(2, customerDao.getAll().size());

    }

    @Disabled
    @Test
    void testUpdate() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");

        currentSession.save(customer);
        currentSession.close();

        customer.setUsername("username2");

        // currentSession = entityManager.unwrap(Session.class);
        customerDao.update(customer);

        // currentSession = entityManager.unwrap(Session.class);
        Customer updatCustomer = currentSession.get(Customer.class, customer.getId());

        assertEquals("username2", updatCustomer.getUsername());

    }

    @Disabled
    @Test
    void testDeleteById() throws Exception {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");
        // currentSession.beginTransaction();
        currentSession.save(customer);

        Customer savedCustomer = currentSession.get(Customer.class, customer.getId());

        customerDao.delete(savedCustomer.getId());

        // start a new currentSession
        currentSession = entityManager.unwrap(Session.class);
        // currentSession.beginTransaction();
        Customer deletedCustomer = customerDao.get(customer.getId());
        currentSession.close();

        assertTrue(deletedCustomer.getId() == null);
    }
}
