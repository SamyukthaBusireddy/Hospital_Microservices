package com.eda.schema.producer.controller;

import com.eda.schema.producer.service.OrderProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/avro/orders")
public class OrderController {

    @Autowired
    private OrderProducerService producerService;

    @PostMapping
    public ResponseEntity<String> sendOrder(@RequestBody Map<String, Object> request) {

        String orderId = (String) request.get("orderId");
        String customerId = (String) request.get("customerId");
        String productId = (String) request.get("productId");
        int quantity = ((Number) request.get("quantity")).intValue();
        double totalAmount = ((Number) request.get("totalAmount")).doubleValue();

        producerService.sendOrder(orderId, customerId, productId, quantity, totalAmount);

        return ResponseEntity.accepted().body(
                " Order sent to order-events");
    }
}

