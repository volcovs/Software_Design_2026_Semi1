package com.example.productmicroservice.services;

import com.example.productmicroservice.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product createProduct(Product product);
    public void deleteProduct(Long Id);
    public Product getById(Long Id);
    public Product updateProduct(Long Id, Product product);
}
