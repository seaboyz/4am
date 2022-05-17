package com.webdev.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    
    @Transactional
    public Product getProductById(Integer id) {
        Optional<Product> product = productDao.get(id);

        if (!product.isPresent()) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }

        return product.get();
    }

    @Transactional
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }
}
