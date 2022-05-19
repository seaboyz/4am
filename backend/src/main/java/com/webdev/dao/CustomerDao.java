package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    private EntityManager entityManager;

    private Session currentSession;

    @Autowired
    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.currentSession = entityManager.unwrap(Session.class);
    }

    public Customer add(Customer customer) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.save(customer);
        return customer;
    }

    public Customer get(Integer id) throws EntityNotFoundException {
        currentSession = entityManager.unwrap(Session.class);
        return currentSession.get(Customer.class, id);

    }

    public Customer getbyEmail(String email) throws NoResultException {
        currentSession = entityManager.unwrap(Session.class);

        Customer customerFromDb = currentSession
                .createQuery("from Customer where email = :email", Customer.class)
                .setParameter("email", email).getSingleResult();

        return customerFromDb;
    }

    public List<Customer> getAll() {
        currentSession = entityManager.unwrap(Session.class);
        List<Customer> customers = currentSession.createQuery("from Customer", Customer.class).list();
        return customers;
    }

    public Customer update(Customer customer) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();

        Customer customerToUpdate = currentSession.get(Customer.class, customer.getId());

        currentSession.evict(customerToUpdate);

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getAddresses().size() != customerToUpdate.getAddresses().size()) {
            customerToUpdate.setAddresses(customer.getAddresses());
        }

        currentSession.merge(customerToUpdate);
        currentSession.getTransaction().commit();

        return customerToUpdate;
    }

    public void delete(Integer id) {
        currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, id);
        currentSession.delete(customer);
    }

    public void delete(Customer customer) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(customer);
    }

    public Customer addAddress(Customer customer, Address address) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        // manually add address to database, so trun off the cascade in the customer entity
        currentSession.save(address);
        customer.getAddresses().add(address);
        currentSession.merge(customer);
        currentSession.getTransaction().commit();

        return customer;
    }
}
