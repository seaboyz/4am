package com.webdev.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.testing.junit4.CustomParameterized.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressDaoTest {

        @Autowired
        private EntityManager entityManager;

        private AddressDao addressDao;

        private Session currentSession;

        private Customer customer1;

        private Address address1;

        private Address address2;

        @BeforeEach
        public void init() {

                addressDao = new AddressDao(entityManager);

                currentSession = entityManager.unwrap(Session.class);

                customer1 = new Customer(
                                "test",
                                "test@test.com",
                                "123456",
                                "555-555-5555");

                address1 = new Address(
                                customer1,
                                "firstname",
                                "lastname",
                                "street",
                                "street2",
                                "city",
                                "state",
                                "zip",
                                "country");

                address2 = new Address(
                                customer1,
                                "firstname2",
                                "lastname2",
                                "street2",
                                "street22",
                                "city2",
                                "state2",
                                "zip2",
                                "country2");
        }

        @AfterEach
        public void tear() {
                currentSession.close();
        }

        @Order(1)
        @Test
        void testAdd() {

                currentSession.save(customer1);

                assertEquals(address1, addressDao.add(address1));
        }

        @Order(2)
        @Test
        void testGet() {

                assertEquals(address1.getCity(), addressDao.get(1).getCity());
        }

        @Order(3)
        @Test
        void testGetAll() {

                assertEquals(1, addressDao.getAll().size());
        }

        // @Disabled
        @Order(4)
        @Test
        void testUpdate() {

                Address addressToUpdate = addressDao.get(1);

                addressToUpdate.setFirstName("jones");

                assertEquals(addressToUpdate.getFirstName(), addressDao.update(addressToUpdate).getFirstName());
        }

        @Disabled
        @Order(5)
        @Test
        void testDelete() {

                Address addressToDelete = addressDao.get(1);

                addressDao.delete(addressToDelete);

                currentSession.close();

                assertNull(addressDao.get(1));

        }

        @Disabled
        @Order(6)
        @Test
        void testDelete2() {
                addressDao.delete(address2);

                assertNull(addressDao.get(2));

                assertEquals(0, addressDao.getAll().size());

        }

}
