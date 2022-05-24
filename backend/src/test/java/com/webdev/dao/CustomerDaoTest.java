package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;



import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDaoTest {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    CustomerDao customerDao;

    Session currentSession;

    Customer customer1;

    @BeforeEach
    public void init() {
        currentSession = sessionFactory.getCurrentSession();
        customer1 = new Customer(
                "username1",
                "email1",
                "password1",
                "123456789");
    }

    @AfterEach
    public void tearDown() {
        currentSession.close();
    }

    // @Disabled
    @Test
    @Order(1)
    void testAdd() {

        assertEquals(customer1.getUsername(), customerDao.add(customer1).getUsername());
    }

    @Test
    @Order(2)
    void testGet() {
        assertEquals(customer1.getUsername(), customerDao.get(1).getUsername());
    }

    @Test
    @Order(3)
    void testGetAll() {
        Customer customer2 = new Customer(
                "username2",
                "email2",
                "password",
                "123456789");

        currentSession.save(customer2);

        assertEquals(2, customerDao.getAll().size());

    }

    @Test
    @Order(4)
    void testUpdate() {
        Customer customerToUpdate = currentSession.get(Customer.class, 1);

        customerToUpdate.setUsername("newUsername1");

        assertEquals(customerToUpdate.getUsername(), customerDao.update(customerToUpdate).getUsername());

    }
}
