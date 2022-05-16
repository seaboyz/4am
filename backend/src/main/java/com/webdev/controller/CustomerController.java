package com.webdev.controller;

import com.webdev.dao.CustomerDao;
import com.webdev.model.Customer;
import com.webdev.utils.TestUtil;

import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @GetMapping("/customers")
    public String getCustomers() {
        SessionFactory sessionFactory = TestUtil.getSessionFactory();

        CustomerDao customerDao = new CustomerDao(sessionFactory);

        Customer customer = new Customer(
                "johnd",
                "john@gmail.com",
                "m38rmF$",
                "1-570-236-7033");

        customerDao.add(customer);

        return customer.toString();

    }
}
