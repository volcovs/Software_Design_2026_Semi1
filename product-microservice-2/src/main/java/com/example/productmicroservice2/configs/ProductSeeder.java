package com.example.productmicroservice2.configs;

import com.example.productmicroservice2.model.Product;
import com.example.productmicroservice2.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements ApplicationRunner {
    private final IProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            return;
        }

        Product p1 = new Product(
                "Product 1",
                "Some description",
                5.70F,
                40);

        Product p2 = new Product(
                "Product 2",
                "Some other description",
                65.30F,
                10);

        Product p3 = new Product(
                "Product 3",
                "Different description",
                12.50F,
                6);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
    }
}
