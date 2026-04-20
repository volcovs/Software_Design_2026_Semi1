package com.example.productmicroservice.contracts;

import com.example.productmicroservice2.model.Product;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;

    public static Product toEntity(ProductRequest request) {
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity());
    }
}
