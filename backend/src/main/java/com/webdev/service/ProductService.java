package com.webdev.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    @Transactional
    public Product getProductById(Integer id) throws NoSuchElementException {
        return productDao.get(id);
    }

    @Transactional
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }
}
