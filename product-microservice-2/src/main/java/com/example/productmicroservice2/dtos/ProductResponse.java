package com.example.productmicroservice2.dtos;

import com.example.productmicroservice2.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Integer stockQuantity;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
