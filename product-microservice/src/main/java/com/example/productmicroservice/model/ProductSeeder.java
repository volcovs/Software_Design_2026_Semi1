package com.example.productmicroservice.model;

import com.example.productmicroservice.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductSeeder implements ApplicationRunner {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            return;
        }

        Product p1 = new Product("Product 1", "Product description", 6.50F, 4);
        Product p2 = new Product("Product 2", "", 12.70F, 5);
        Product p3 = new Product("Product 3", "Product 3 description", 2.30F, 6);

        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
    }

}
