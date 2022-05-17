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

    @Autowired
    private EntityManager entityManager;

    @Override
    public Customer add(Customer customer) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.save(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
        return customer;
    }

    @Override
    public Optional<Customer> get(Integer id) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Optional<Customer> customer = Optional.ofNullable(currentSession.get(Customer.class, id));
        currentSession.getTransaction().commit();
        currentSession.close();
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        List<Customer> customers = currentSession.createQuery("from Customer", Customer.class).list();

        currentSession.getTransaction().commit();

        return customers;
    }

    @Override
    public Customer update(Customer customer) {
        Session currentSession = entityManager.unwrap(Session.class);
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
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        Customer customer = currentSession.get(Customer.class, id);
        currentSession.delete(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    @Override
    public void delete(Customer customer) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        currentSession.delete(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
    }
}
