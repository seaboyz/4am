package com.webdev.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.dao.AddressDao;
import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class AddressServiceTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressDao addressDao;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerDao customerDao;
    Session currentSession;

    @BeforeEach
    public void setUp() {
        currentSession = entityManager.unwrap(Session.class);

    }

    @AfterEach
    void tearDown() {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.createQuery("delete from Address").executeUpdate();
        currentSession.createQuery("delete from Customer").executeUpdate();
        currentSession.getTransaction().commit();
        currentSession.close();

    }

    @Disabled
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

        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        List<Address> addresses = currentSession.createQuery("from Address", Address.class).list();
        currentSession.getTransaction().commit();

        assert (addresses.size() == 1);

        Optional<Customer> optionalCustomer = customerService.getCustomerById(customer.getId());

        assert (optionalCustomer.get().getAddresses().size() == 1);

    }
}
