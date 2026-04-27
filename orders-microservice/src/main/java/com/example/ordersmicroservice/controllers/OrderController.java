package com.example.ordersmicroservice.controllers;


import com.example.ordersmicroservice.contracts.OrderRequest;
import com.example.ordersmicroservice.dtos.OrderDtos;
import com.example.ordersmicroservice.model.Order;
import com.example.ordersmicroservice.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping
    public List<OrderDtos.OrderResponse> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDtos.OrderResponse> result = new ArrayList<>();

        for (Order p: orders){
            result.add(OrderDtos.OrderResponse.fromEntity(p));
        }

        return result;
    }

    @PostMapping
    public OrderDtos.OrderResponse createOrder(@RequestBody OrderRequest request) {
        Order toCreate = OrderRequest.toEntity(request);
        return OrderDtos.OrderResponse.fromEntity(orderService.createOrder(toCreate));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("{id}")
    public OrderDtos.OrderResponse getById(@PathVariable Long id) {
        return OrderDtos.OrderResponse.fromEntity(orderService.getById(id));
    }

    @PatchMapping("{id}")
    public OrderDtos.OrderResponse updateOrder(@PathVariable Long id, @RequestBody OrderRequest request) {
        Order toUpdate = OrderRequest.toEntity(request);
        return OrderDtos.OrderResponse.fromEntity(orderService.updateOrder(id, toUpdate));
    }


}
