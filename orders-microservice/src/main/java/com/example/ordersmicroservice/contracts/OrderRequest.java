package com.example.ordersmicroservice.contracts;

import com.example.ordersmicroservice.dtos.OrderDtos;
import com.example.ordersmicroservice.model.Order;
import com.example.ordersmicroservice.model.OrderLine;

import java.util.List;

public class OrderRequest {
    public String customer_name;
    public List<OrderLine> lines;


    public static Order toEntity(OrderRequest request) {
        return new Order(
                request.customer_name,
                request.lines
        );
    }
}
