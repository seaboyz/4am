package com.webdev.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional(readOnly = true)
    public Product getProductById(Integer id) throws NoSuchElementException {
        return productDao.get(id);

    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }
}
