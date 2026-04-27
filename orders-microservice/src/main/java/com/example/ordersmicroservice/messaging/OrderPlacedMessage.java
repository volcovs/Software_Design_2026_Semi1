package com.example.ordersmicroservice.messaging;

import java.util.List;

public record OrderPlacedMessage(Long orderId, List<Line> lines) {

    public record Line(long productId, int quantity) {}
}
