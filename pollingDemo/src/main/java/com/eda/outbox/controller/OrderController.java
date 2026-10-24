package com.eda.outbox.controller;

import com.eda.outbox.entity.Order;
import com.eda.outbox.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody Order order) {

        orderService.createOrder(order);
        return "Order created";
    }
}

