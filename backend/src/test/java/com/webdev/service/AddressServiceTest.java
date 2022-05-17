package com.webdev.service;

import java.util.List;

import com.webdev.dao.AddressDao;
import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;
import com.webdev.utils.TestUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// integration test
public class AddressServiceTest {
    static SessionFactory sessionFactory;
    AddressService addressService;
    AddressDao addressDao;
    CustomerService customerService;
    CustomerDao customerDao;
    Session session;

    @BeforeAll
    static void init() {
        sessionFactory = TestUtil.getSessionFactory();
    }

    @BeforeEach
    void setUp() {
        addressDao = new AddressDao(sessionFactory);
        customerDao = new CustomerDao(sessionFactory);
        customerService = new CustomerService(customerDao);
        addressService = new AddressService(addressDao, customerService);
        session = sessionFactory.openSession();
    }

    @AfterEach
    void tearDown() {
        sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Address").executeUpdate();
        session.createQuery("delete from Customer").executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @Test
    void testAddAddressToCustomer() throws Exception {
        Customer customer = new Customer("johnd", "john@gmail.com", "m38rmF", "1-570-236-7033");

        customerDao.add(customer);

        ShippingAddress shippingAddress = new ShippingAddress(
                "david",
                "morrison",
                "86 Frances Ct",
                "",
                "Cullman",
                "TN",
                "38301",
                "USA");

        addressService.addAddressToCustomer(customer.getId(), shippingAddress);

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Address> addresses = session.createQuery("from Address", Address.class).list();
        session.getTransaction().commit();

        assert (addresses.size() == 1);

        customer = customerService.getCustomerById(customer.getId());

        assert (customer.getAddresses().size() == 1);

        

    }
}
