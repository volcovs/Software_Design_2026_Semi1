package com.example.productmicroservice2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name="products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String Name;

    private String Description;

    @Column(nullable = false, precision = 2)
    private Float Price;

    private Integer StockQuantity;

    private Instant CreatedAt = Instant.now();

    public Product(String name, String description, Float price, Integer stockQuantity) {
        this.Name = name;
        this.Description = description;
        this.Price = price;
        this.StockQuantity = stockQuantity;
    }
}
