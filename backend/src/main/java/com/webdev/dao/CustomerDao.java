package com.webdev.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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

    public Optional<Customer> add(Customer customer) {

        currentSession = entityManager.unwrap(Session.class);
        currentSession.save(customer);
        currentSession.close();
        if (customer.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    public Optional<Customer> get(Integer id) {
        currentSession = entityManager.unwrap(Session.class);
        Optional<Customer> customer = Optional.ofNullable(currentSession.get(Customer.class, id));
        currentSession.close();
        return customer;
    }

    public Optional<Customer> getbyEmail(String email) {
        currentSession = entityManager.unwrap(Session.class);
        Optional<Customer> customer = Optional
                .ofNullable(currentSession.createQuery("from Customer where email = :email", Customer.class)
                        .setParameter("email", email).getSingleResult());
        currentSession.close();
        return customer;
    }

    public List<Customer> getAll() {
        currentSession = entityManager.unwrap(Session.class);
        List<Customer> customers = currentSession.createQuery("from Customer", Customer.class).list();
        currentSession.close();
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
        currentSession.close();

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

    public void addAddress(Customer customer, Address address) {
        currentSession = entityManager.unwrap(Session.class);
        currentSession.beginTransaction();
        // manually add address to database, so trun off the cascade in the customer entity
        currentSession.save(address);
        customer.getAddresses().add(address);
        currentSession.merge(customer);
        currentSession.getTransaction().commit();
        currentSession.close();
    }
}
