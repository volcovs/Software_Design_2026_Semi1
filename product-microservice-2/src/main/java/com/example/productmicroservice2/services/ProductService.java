package com.example.productmicroservice2.services;

import com.example.productmicroservice2.model.Product;
import com.example.productmicroservice2.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }

        Product modified = existing.get();

        modified.setName(product.getName());
        modified.setDescription(product.getDescription());
        modified.setPrice(product.getPrice());
        modified.setStockQuantity(product.getStockQuantity());

        return productRepository.save(modified);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProduct(Long id) {
        Optional<Product> res = productRepository.findById(id);
        return res.orElse(null);
    }
}
