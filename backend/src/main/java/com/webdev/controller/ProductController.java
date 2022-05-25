package com.webdev.controller;

import com.google.gson.Gson;
import com.webdev.model.Product;
import com.webdev.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    @ResponseBody
    public String getProductsById(@PathVariable String id) {
        Integer productId = Integer.parseInt(id);

        Product productFromDb = productService.getProductById(productId);

        return new Gson().toJson(productFromDb, Product.class);
    }

    @GetMapping("/products")
    @ResponseBody
    public String getAllProducts() {
        return new Gson().toJson(productService.getAllProducts());
    }
}
