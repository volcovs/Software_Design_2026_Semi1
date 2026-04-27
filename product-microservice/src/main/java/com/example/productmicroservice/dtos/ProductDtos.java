package com.example.productmicroservice.dtos;

import com.example.productmicroservice.model.Product;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
public class ProductDtos {

    public record ProductResponse(
            Long Id,
            String name,
            String description,
            Float price,
            Integer stock_quantity
    ) {
        public static ProductResponse fromEntity(Product p) {
            return new ProductResponse(p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getStock_quantity());
        }
    }


}
