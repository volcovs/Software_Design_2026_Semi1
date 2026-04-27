package com.example.ordersmicroservice.repositories;

import com.example.ordersmicroservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
