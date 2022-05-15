package com.webdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.google.gson.Gson;
import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private ProductService productService;
    private Product product;
    private Gson gson;

    @Mock
    private static ProductDao productDao;

    @BeforeEach
    public void setUp() {
        // init gson
        gson = new Gson();
        // init productService
        productService = new ProductService(productDao);
        // init product
        product = new Product(
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                109.95,
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "men's clothing");
        product.setId(1);
    }

    @AfterEach
    public void tearDown() {
        product = null;
        productService = null;
        gson = null;
    }

    @Test
    void testGetProductById() throws NoSuchElementException {
        when(productDao.get(1)).thenReturn(Optional.ofNullable(product));

        Product productFromDb = productService.getProductById(1);

        assertEquals(gson.toJson(
                product, Product.class),
                gson.toJson(productFromDb, Product.class));

    }
}
