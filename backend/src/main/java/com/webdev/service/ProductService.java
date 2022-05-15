package com.webdev.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProductById(Integer id) {
        Optional<Product> product = productDao.get(id);

        if (!product.isPresent()) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }

        return product.get();
    }
}
