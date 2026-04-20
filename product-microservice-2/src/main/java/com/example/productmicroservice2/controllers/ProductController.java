package com.example.productmicroservice2.controllers;

import com.example.productmicroservice2.dtos.ProductResponse;
import com.example.productmicroservice2.model.Product;
import com.example.productmicroservice2.services.IProductService;
import com.example.productmicroservice.contracts.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> resp = new ArrayList<>();

        for(Product p: products) {
            resp.add(ProductResponse.fromEntity(p));
        }

        return resp;
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return ProductResponse.fromEntity(productService.getProduct(id));
    }

    @PostMapping()
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        Product product = ProductRequest.toEntity(request);
        return ProductResponse.fromEntity(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product product = ProductRequest.toEntity(request);
        return ProductResponse.fromEntity(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
