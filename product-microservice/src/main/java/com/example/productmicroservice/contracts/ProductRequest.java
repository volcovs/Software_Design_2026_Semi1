package com.example.productmicroservice.contracts;

import com.example.productmicroservice.model.Product;

public class ProductRequest {
    public String name;
    public String description;
    public Float price;
    public Integer stock_quantity;


    public static Product toEntity(ProductRequest request) {
        return new Product(
                request.name,
                request.description,
                request.price,
                request.stock_quantity
        );
    }
}
