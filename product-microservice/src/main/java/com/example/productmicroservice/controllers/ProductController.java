package com.example.productmicroservice.controllers;

import com.example.productmicroservice.contracts.ProductRequest;
import com.example.productmicroservice.dtos.ProductDtos;
import com.example.productmicroservice.model.Product;
import com.example.productmicroservice.services.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDtos.ProductResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDtos.ProductResponse> result = new ArrayList<>();

        for (Product p: products){
            result.add(ProductDtos.ProductResponse.fromEntity(p));
        }

        return result;
    }

    @PostMapping
    public ProductDtos.ProductResponse createProduct(@RequestBody ProductRequest request) {
        Product toCreate = ProductRequest.toEntity(request);
        return ProductDtos.ProductResponse.fromEntity(productService.createProduct(toCreate));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("{id}")
    public ProductDtos.ProductResponse getById(@PathVariable Long id) {
        return ProductDtos.ProductResponse.fromEntity(productService.getById(id));
    }

    @PutMapping("{id}")
    public ProductDtos.ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product toUpdate = ProductRequest.toEntity(request);
        return ProductDtos.ProductResponse.fromEntity(productService.updateProduct(id, toUpdate));
    }
}
