package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.webdev.model.Customer;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao implements Dao<Customer> {

    private EntityManager entityManager;

    private Session currentSession;

    @Autowired
    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.currentSession = entityManager.unwrap(Session.class);
    }

    @Override
    public Customer add(Customer customer) {

        currentSession = entityManager.unwrap(Session.class);
        currentSession.save(customer);
        currentSession.close();
        return customer;
    }

    @Override
    public Optional<Customer> get(Integer id) {
        currentSession = entityManager.unwrap(Session.class);
        Optional<Customer> customer = Optional.ofNullable(currentSession.get(Customer.class, id));
        currentSession.close();
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        currentSession = entityManager.unwrap(Session.class);
        List<Customer> customers = currentSession.createQuery("from Customer", Customer.class).list();
        currentSession.close();
        return customers;
    }

    @Override
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
        currentSession.close();

        return customerToUpdate;
    }

    @Override
    public void delete(Integer id) {
        currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, id);
        currentSession.delete(customer);
    }

    @Override
    public void delete(Customer customer) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(customer);
    }
}
