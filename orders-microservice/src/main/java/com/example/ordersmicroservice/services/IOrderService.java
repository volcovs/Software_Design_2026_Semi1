package com.example.ordersmicroservice.services;

import com.example.ordersmicroservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    public List<Order> getAllOrders();
    public Order createOrder(Order order);
    public void deleteOrder(Long Id);
    public Order getById(Long Id);
    public Order updateOrder(Long Id, Order order);
}
