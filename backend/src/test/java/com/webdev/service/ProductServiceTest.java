package com.webdev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.google.gson.Gson;
import com.webdev.dao.ProductDao;
import com.webdev.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    Gson gson = new Gson();

    Product product;

    @Mock
    private static ProductDao productDao;

    @BeforeEach
    public void setUp() {
        product = new Product(
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                109.95,
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "men's clothing");
        product.setId(1);
    }

    @Disabled
    @Test
    void testGetProductById() throws NoSuchElementException {
        when(productDao.get(1)).thenReturn(Optional.ofNullable(product));

        Product productFromDb = productService.getProductById(1);

        assertEquals(gson.toJson(
                product, Product.class),
                gson.toJson(productFromDb, Product.class));

    }

    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>();

        products.add(product);
        when(productDao.getAll()).thenReturn(products);

        List<Product> allProducts = productService.getAllProducts();

        assertEquals(1, allProducts.size());
    }
}
