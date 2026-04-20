package com.example.productmicroservice2.services;

import com.example.productmicroservice2.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product createProduct(Product product);
    public Product updateProduct(Long id, Product product);
    public Product getProduct(Long id);
    public void deleteProduct(Long id);
}
