package com.webdev.service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Address;
import com.webdev.model.Customer;
import com.webdev.model.ShippingAddress;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer createCustomer(Customer customer) {
        return customerDao.add(customer);

    }

    public Customer getCustomerById(Integer id) throws EntityNotFoundException {
        return customerDao.get(id);
    }

    public Customer getCustomerByEmail(String email) throws EntityNotFoundException, NoResultException {
        return customerDao.getbyEmail(email);
    }

    // @Transactional
    // it doesn't not have to be transactional, because it only save the customerId on the addresses table
    public Customer addAddressToCustomer(
            Integer customerId,
            ShippingAddress shippingAddress)
            throws EntityNotFoundException {

        Customer customer = customerDao.get(customerId);

        Address address = new Address(
                customer,
                shippingAddress.getFirstName(),
                shippingAddress.getLastName(),
                shippingAddress.getStreet(),
                shippingAddress.getStreet2(),
                shippingAddress.getCity(),
                shippingAddress.getState(),
                shippingAddress.getZip(),
                shippingAddress.getCountry());

        return customerDao.addAddress(customer, address);
    }
}
