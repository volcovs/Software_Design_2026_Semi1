package com.example.ordersmicroservice.dtos;

import com.example.ordersmicroservice.model.Order;
import com.example.ordersmicroservice.model.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class OrderDtos {

    public record OrderResponse(
        Long Id,
        String Customer_Name,
        List<OrderLineDtos.OrderLineResponse> lines
    )

        {
            public static OrderResponse fromEntity (Order o){
                List<OrderLine> temp = o.getLines();
                List<OrderLineDtos.OrderLineResponse> lines = new ArrayList<>();
                for (OrderLine ol: temp) {
                    lines.add(OrderLineDtos.OrderLineResponse.fromEntity(ol));
                }

                return new OrderResponse(o.getId(),
                        o.getCustomer_Name(),
                        lines);
            }
        }
}
