package com.webdev.service;

import com.webdev.dao.CustomerDao;

import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthSerivice {
    private CustomerDao customerDao;

    @Autowired
    public AuthSerivice(CustomerDao customerDao) throws NoSuchParameterException {
        this.customerDao = customerDao;
    }
}
