package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressDaoTest {

    @Autowired
    private EntityManager entityManager;

    private AddressDao addressDao;

    private Session currentSession;

    @BeforeEach
    public void init() {
        addressDao = new AddressDao(entityManager);
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
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");

        addressDao.add(address);

        currentSession = entityManager.unwrap(Session.class);
        Address addressFromDb = currentSession.get(Address.class, address.getId());
        assertEquals(address.toString(), addressFromDb.toString());
    }

    @Test
    void testGet() {
        Customer customer = new Customer(
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");

        currentSession.save(address);

        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(addressFromDb.isPresent());
        assertEquals(address.toString(), addressFromDb.get().toString());

    }

    @Test
    void testGetAll() {
        Customer customer = new Customer(
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address1 = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");
        Address address2 = new Address(
                customer,
                "firstname2",
                "lastname2",
                "street2",
                "street22",
                "city2",
                "state2",
                "zip2",
                "country2");

        currentSession.save(address1);
        currentSession.save(address2);
        assertEquals(2, addressDao.getAll().size());

    }

    @Test
    void testUpdate() {

        Customer customer = new Customer(
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");
        currentSession.save(address);
        currentSession.close();

        address.setFirstName("john");
        address.setLastName("doe");
        address.setStreet("123 main st");
        address.setStreet2("apt 1");
        address.setCity("anytown");
        address.setState("CA");
        address.setZip("12345");
        address.setCountry("USA");

        currentSession = entityManager.unwrap(Session.class);
        Address addressFromDb = currentSession.get(Address.class, address.getId());

        assertEquals(address.toString(), addressFromDb.toString());

    }

    @Test
    void testDelete() {
        Customer customer = new Customer(
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");

        currentSession.beginTransaction();
        currentSession.save(address);
        currentSession.getTransaction().commit();
        currentSession.close();
        // currentSession is closed in userdao.add()

        // delete the address
        addressDao.delete(address);
        // currentSession is closed in userdao.delete()

        // check if the address was deleted from the database
        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(!addressFromDb.isPresent());

    }

    @Test
    void testDelete2() {
        Customer customer = new Customer(
                "test",
                "test@test.com",
                "123456",
                "555-555-5555");
        Address address = new Address(
                customer,
                "firstname",
                "lastname",
                "street",
                "street2",
                "city",
                "state",
                "zip",
                "country");

        addressDao.add(address);
        // currentSession is closed in userdao.add()

        // delete the address
        addressDao.delete(address);
        // currentSession is closed in userdao.delete()

        // check if the address was deleted from the database
        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(!addressFromDb.isPresent());

    }

}
