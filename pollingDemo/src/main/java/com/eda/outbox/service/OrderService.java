package com.eda.outbox.service;

import com.eda.outbox.entity.Order;
import com.eda.outbox.entity.OutboxEvent;
import com.eda.outbox.repository.OrderRepository;
import com.eda.outbox.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Transactional
    public Order createOrder(Order order) {

        // Step 1: save to orders table
        Order savedOrder = orderRepository.save(order);

        // Step 2: Create outbox event IN THE SAME TRANSACTION
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType("Order");
        outboxEvent.setAggregateId(savedOrder.getOrderId());
        outboxEvent.setEventType("ORDER_CREATED");

        // Payload: JSON string of the event data
        String payload = String.format(
            "{\"orderId\":\"%s\"," +
                    "\"quantity\":%d," +
                    "\"totalAmount\":%.2f}" ,
            savedOrder.getOrderId(),
            savedOrder.getQuantity(),
            savedOrder.getTotalAmount()
        );
        outboxEvent.setPayload(payload);

        outboxRepository.save(outboxEvent);

        return savedOrder;
    }
}

