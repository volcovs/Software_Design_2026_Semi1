package com.example.ordersmicroservice.services;

import com.example.ordersmicroservice.messaging.OrderPlacedPublisher;
import com.example.ordersmicroservice.model.Order;
import com.example.ordersmicroservice.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private OrderPlacedPublisher orderPlacedPublisher;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
       Order saved = orderRepository.save(order);
       orderPlacedPublisher.publish(saved);

       return saved;
    }

    public void deleteOrder(Long Id) {
        orderRepository.deleteById(Id);
    }

    public Order getById(Long Id) {
        Optional<Order> order = orderRepository.findById(Id);
        return order.orElse(null);
    }

    public Order updateOrder(Long Id, Order order) {
        Optional<Order> res = orderRepository.findById(Id);
        if (res.isEmpty()) {
            return null;
        }

        Order existing = res.get();

        existing.setCustomer_Name(order.getCustomer_Name());
        existing.setStatus(order.getStatus());
        existing.setLines(order.getLines());
        return orderRepository.save(existing);
    }
}
