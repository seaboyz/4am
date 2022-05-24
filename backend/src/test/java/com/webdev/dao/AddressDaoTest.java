package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webdev.model.Address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class AddressDaoTest {
    @Autowired
    private static SessionFactory sessionFactory;

    @Autowired
    private static AddressDao addressDao;

    private Session currentSession;

    @BeforeEach
    public void init() {
        currentSession = sessionFactory.getCurrentSession();
    }

    @AfterEach
    public void tear() {
        if (currentSession != null)
            currentSession.close();
    }

    @AfterEach
    public void tearDown() {
        if (currentSession != null)
            currentSession.close();
    }

    @Test
    void testAdd() {
        Address address = new Address(
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

        // check if the address was added to the database
        Address addressFromDb = currentSession.get(Address.class, address.getId());
        assertEquals(address.toString(), addressFromDb.toString());
    }

    @Test
    void testGet() {
        Address address = new Address(
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

        // check if the address was added to the database
        Address addressFromDb = addressDao.get(address.getId());

        assertEquals(address.toString(), addressFromDb.toString());

    }

    @Test
    void testUpdate() {
        // create a new address
        Address address = new Address(
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
        // address is detached

        // update the address
        address.setFirstName("john");
        address.setLastName("doe");
        address.setStreet("123 main st");
        address.setStreet2("apt 1");
        address.setCity("anytown");
        address.setState("CA");
        address.setZip("12345");
        address.setCountry("USA");

        // update the address in the database
        addressDao.update(address);
        // currentSession is closed in userdao.update()

        // check if the address was updated in the database
        currentSession.beginTransaction();
        Address addressFromDb = currentSession.get(Address.class, address.getId());
        currentSession.getTransaction().commit();

        assertEquals(address.toString(), addressFromDb.toString());

    }

    @Test
    void testDelete() {
        Address address = new Address(
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
        Address addressFromDb = addressDao.get(address.getId());
        assertTrue(addressFromDb != null);

    }

    @Test
    void testDelete2() {
        Address address = new Address(
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
        Address addressFromDb = addressDao.get(address.getId());
        assertTrue(addressFromDb != null);

    }

}
