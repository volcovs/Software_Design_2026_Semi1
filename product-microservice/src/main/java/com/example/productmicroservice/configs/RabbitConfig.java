package com.example.productmicroservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "demo.bakery.topic";
    public static final String ORDER_PLACED = "order.placed";
    public static final String ORDER_PLACED_QUEUE = "order.placed.queue";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    Queue orderPlacedQueue() {
        return new Queue(ORDER_PLACED_QUEUE, true);
    }

    @Bean
    Binding orderPlacedBinding(Queue orderPlacedQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(orderPlacedQueue).to(topicExchange).with(ORDER_PLACED);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
