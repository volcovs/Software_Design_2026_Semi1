package com.example.productmicroservice.services;

import com.example.productmicroservice.model.Product;
import com.example.productmicroservice.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long Id) {
        productRepository.deleteById(Id);
    }

    public Product getById(Long Id) {
        Optional<Product> product = productRepository.findById(Id);
        return product.orElse(null);
    }

    public Product updateProduct(Long Id, Product product) {
        Optional<Product> res = productRepository.findById(Id);
        if (res.isEmpty()) {
            return null;
        }

        Product existing = res.get();

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock_quantity(product.getStock_quantity());
        return productRepository.save(existing);
    }
}
