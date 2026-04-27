package com.example.ordersmicroservice.messaging;

import com.example.ordersmicroservice.configs.RabbitConfig;
import com.example.ordersmicroservice.model.Order;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderPlacedPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Order order) {
        if (order.getId() == null) {
            throw new IllegalStateException("Order id must be set before publishing");
        }
        List<OrderPlacedMessage.Line> lines = order.getLines() == null
                ? List.of()
                : order.getLines().stream()
                        .filter(l -> l.getProduct_Id() != null)
                        .map(
                                l -> new OrderPlacedMessage.Line(
                                        l.getProduct_Id(), l.getQuantity() != null ? l.getQuantity() : 0))
                        .toList();
        if (lines.isEmpty()) {
            return;
        }
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ORDER_PLACED, new OrderPlacedMessage(order.getId(), lines));
    }
}
