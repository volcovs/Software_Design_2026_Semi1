package com.example.ordersmicroservice.dtos;

import com.example.ordersmicroservice.model.Order;
import com.example.ordersmicroservice.model.OrderLine;

public class OrderLineDtos {
    public record OrderLineResponse(
            Long Id,
            Long Product_Id,
            Long Order_Id,
            Integer Quantity,
            Float Unit_Price
    ) {
        public static OrderLineResponse fromEntity(OrderLine o) {
            return new OrderLineResponse(o.getId(),
                    o.getProduct_Id(),
                    o.getOrder_Id(),
                    o.getQuantity(),
                    o.getUnit_Price());
        }
    }
}
