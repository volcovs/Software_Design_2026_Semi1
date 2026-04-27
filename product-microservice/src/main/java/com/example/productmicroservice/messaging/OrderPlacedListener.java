package com.example.productmicroservice.messaging;

import com.example.productmicroservice.configs.RabbitConfig;
import com.example.productmicroservice.model.Product;
import com.example.productmicroservice.repositories.IProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedListener {

    private final ObjectMapper objectMapper;
    private final IProductRepository productRepository;
    private final TransactionTemplate transactionTemplate;

    @RabbitListener(queues = RabbitConfig.ORDER_PLACED_QUEUE)
    public void onMessage(String body) {
        if (body == null || body.isEmpty()) {
            return;
        }
        OrderPlacedMessage message;
        try {
            message = objectMapper.readValue(body, OrderPlacedMessage.class);
        } catch (Exception e) {
            log.error("Invalid order message: {}", e.getMessage());
            return;
        }
        if (message.lines() == null || message.lines().isEmpty()) {
            return;
        }
        try {
            transactionTemplate.executeWithoutResult(status -> applyStock(message));
        } catch (Exception e) {
            log.error("Could not update stock for order {}: {}", message.orderId(), e.getMessage());
        }
    }

    private void applyStock(OrderPlacedMessage message) {
        Map<Long, Integer> byProduct = new HashMap<>();
        for (OrderPlacedMessage.Line line : message.lines()) {
            if (line.quantity() > 0) {
                byProduct.merge(line.productId(), line.quantity(), Integer::sum);
            }
        }
        if (byProduct.isEmpty()) {
            return;
        }
        for (Map.Entry<Long, Integer> e : byProduct.entrySet()) {
            Product p = productRepository
                    .findById(e.getKey())
                    .orElseThrow(() -> new IllegalStateException("Unknown product " + e.getKey()));
            int onHand = p.getStock_quantity() == null ? 0 : p.getStock_quantity();
            if (onHand < e.getValue()) {
                throw new IllegalStateException("Insufficient stock for product " + e.getKey());
            }
        }
        for (Map.Entry<Long, Integer> e : byProduct.entrySet()) {
            Product p = productRepository.findById(e.getKey()).orElseThrow();
            int onHand = p.getStock_quantity() == null ? 0 : p.getStock_quantity();
            p.setStock_quantity(onHand - e.getValue());
        }
    }
}

