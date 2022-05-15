package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.webdev.model.Customer;
import com.webdev.utils.TestUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerDaoTest {
    private static SessionFactory sessionFactory;
    private Session session;
    private static CustomerDao customerDao;

    @BeforeAll
    public static void setUpBeforeAllTests() {
        sessionFactory = TestUtil.getSessionFactory();
        System.out.println("SessionFactory created.");
        customerDao = new CustomerDao(sessionFactory);
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
    }

    @AfterEach
    public void closeSession() {
        if (session != null)
            session.close();

        // remove all customers from the database
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Customer").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Test
    void testAdd() {
        Customer customer = new Customer(
                "username1",
                "email1",
                "password",
                "phoneNumber");

        customerDao.add(customer);
        // session is closed in userdao.add()

        session = sessionFactory.openSession();
        session.beginTransaction();
        Customer savedCustomer = session.get(Customer.class, customer.getId());

        assertEquals(customer.getUsername(), savedCustomer.getUsername());
    }

    @Test
    void testGet() {
        Customer customer = new Customer(
                "username",
                "email",
                "password",
                "phoneNumber");
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
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

        assertEquals(2, customerDao.getAll().size());

    }

    @Test
    void testUpdate() {
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

        customer.setUsername("username2");

        customerDao.update(customer);

        // start a new session
        session = sessionFactory.openSession();
        session.beginTransaction();
        Customer updatCustomer = session.get(Customer.class, customer.getId());
        session.getTransaction().commit();
        session.close();

        assertEquals("username2", updatCustomer.getUsername());

    }

    @Test
    void testDelete() {
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

        customerDao.delete(customer);

        // start a new session
        session = sessionFactory.openSession();
        session.beginTransaction();
        Optional<Customer> deletedCustomer = customerDao.get(customer.getId());
        session.getTransaction().commit();
        session.close();

        assertTrue(!deletedCustomer.isPresent());
    }
}
