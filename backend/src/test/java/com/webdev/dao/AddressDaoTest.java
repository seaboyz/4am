package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.webdev.model.Address;
import com.webdev.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressDaoTest {
    private static SessionFactory sessionFactory;
    private Session session;
    private static AddressDao addressDao;

    @BeforeAll
    public static void setUpBeforeAllTests() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory created.");
        addressDao = new AddressDao(sessionFactory);
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
        session.createQuery("delete from Address").executeUpdate();

        session.getTransaction().commit();
        session.close();
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
        // session is closed in userdao.add()

        // check if the address was added to the database
        session = sessionFactory.openSession();
        Address addressFromDb = session.get(Address.class, address.getId());
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

        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
        session.close();
        // session is closed in userdao.add()

        // check if the address was added to the database
        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(addressFromDb.isPresent());
        assertEquals(address.toString(), addressFromDb.get().toString());

    }

    @Test
    void testGetAll() {
        Address address1 = new Address(
                "firstname1",
                "lastname1",
                "street1",
                "street21",
                "city1",
                "state1",
                "zip1",
                "country1");
        Address address2 = new Address(
                "firstname2",
                "lastname2",
                "street2",
                "street22",
                "city2",
                "state2",
                "zip2",
                "country2");

        session.beginTransaction();
        session.save(address1);
        session.save(address2);
        session.getTransaction().commit();
        session.close();
        // session is closed in userdao.add()

        // start a new session
        assertEquals(2, addressDao.getAll().size());

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
        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
        session.close();
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
        // session is closed in userdao.update()

        // check if the address was updated in the database
        session = sessionFactory.openSession();
        session.beginTransaction();
        Address addressFromDb = session.get(Address.class, address.getId());
        session.getTransaction().commit();

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

        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
        session.close();
        // session is closed in userdao.add()

        // delete the address
        addressDao.delete(address);
        // session is closed in userdao.delete()

        // check if the address was deleted from the database
        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(!addressFromDb.isPresent());

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
        // session is closed in userdao.add()

        // delete the address
        addressDao.delete(address);
        // session is closed in userdao.delete()

        // check if the address was deleted from the database
        Optional<Address> addressFromDb = addressDao.get(address.getId());
        assertTrue(!addressFromDb.isPresent());

    }

}
