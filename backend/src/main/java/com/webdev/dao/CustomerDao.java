package com.webdev.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.model.Address;
import com.webdev.model.Customer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Customer add(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
        return customer;
    }

    public Customer get(Integer id) throws EntityNotFoundException {

        return sessionFactory.getCurrentSession().get(Customer.class, id);

    }

    public Customer getbyEmail(String email) throws NoResultException {

        Customer customerFromDb;

        try {
            customerFromDb = sessionFactory.getCurrentSession()
                    .createQuery("from Customer where email = :email", Customer.class)
                    .setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        }

        return customerFromDb;
    }

    public List<Customer> getAll() {

        List<Customer> customers = sessionFactory.getCurrentSession().createQuery("from Customer", Customer.class)
                .list();
        return customers;
    }

    public Customer update(Integer id, Customer customer) {

        Session currentSession = sessionFactory.openSession();

        currentSession.beginTransaction();

        Customer customerToUpdate = currentSession.get(Customer.class, id);

        customerToUpdate.setUsername(customer.getUsername());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setPhone(customer.getPhone());

        currentSession.getTransaction().commit();

        return customerToUpdate;
    }

    public Customer update(Customer customer) {
        return (Customer) sessionFactory.getCurrentSession().merge(customer);
    }

    public void delete(Integer id) {

        Customer customer = sessionFactory.getCurrentSession().get(Customer.class, id);
        sessionFactory.getCurrentSession().delete(customer);
    }

    public void delete(Customer customer) {

        sessionFactory.getCurrentSession().delete(customer);
    }

    public Customer addAddress(Customer customer, Address address) {

        // sessionFactory.getCurrentSession().beginTransaction();
        // manually add address to database, so trun off the cascade in the customer entity
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        address.setCustomer(customer);
        customer.getAddresses().add(address);
        currentSession.save(customer);
        sessionFactory.getCurrentSession().getTransaction().commit();

        return customer;
    }
}
