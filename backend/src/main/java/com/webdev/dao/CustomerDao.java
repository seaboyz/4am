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

    private Session currentSession;

    @Autowired
    public CustomerDao(EntityManager entityManager) {
        this.currentSession = entityManager.unwrap(Session.class);
    }

    public Customer add(Customer customer) {
        currentSession.save(customer);
        return customer;
    }

    public Customer get(Integer id) throws EntityNotFoundException {

        return currentSession.get(Customer.class, id);

    }

    public Customer getbyEmail(String email) throws NoResultException {

        Customer customerFromDb = currentSession
                .createQuery("from Customer where email = :email", Customer.class)
                .setParameter("email", email).getSingleResult();

        return customerFromDb;
    }

    public List<Customer> getAll() {

        List<Customer> customers = currentSession.createQuery("from Customer", Customer.class).list();
        return customers;
    }

    public Customer update(Customer customer) {

        Customer customerToUpdate = currentSession.get(Customer.class, customer.getId());

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
        if (customer.getAddresses().size() != customerToUpdate.getAddresses().size()) {
            customerToUpdate.setAddresses(customer.getAddresses());
        }

        return customerToUpdate;
    }

    public void delete(Integer id) {

        Customer customer = currentSession.get(Customer.class, id);
        currentSession.delete(customer);
    }

    public void delete(Customer customer) {

        currentSession.delete(customer);
    }

    public Customer addAddress(Customer customer, Address address) {

        // currentSession.beginTransaction();
        // manually add address to database, so trun off the cascade in the customer entity
        currentSession.save(address);
        customer.getAddresses().add(address);
        currentSession.merge(customer);
        currentSession.getTransaction().commit();

        return customer;
    }
}
