package com.example.productmicroservice.repositories;

import com.example.productmicroservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {


}
