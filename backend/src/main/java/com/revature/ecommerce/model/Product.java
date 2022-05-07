package com.revature.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use traditional generator
    private int id;

    @Column(name = "name")
    private @NonNull String name;

    @Column(name = "description")
    private @NonNull String description;

    @Column(name = "price")
    private @NonNull Double price;

    @Column(name = "image_url")
    private @NonNull String imageUrl;

    @Column(name = "category")
    private @NonNull String category;
}
