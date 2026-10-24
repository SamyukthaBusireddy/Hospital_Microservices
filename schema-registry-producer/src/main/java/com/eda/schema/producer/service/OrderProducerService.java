package com.eda.schema.producer.service;

import com.eda.schema.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducerService {


    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(String orderId, String customerId, String productId,
                          int quantity, double totalAmount) {

        // Build Order using Avro's generated Builder pattern
        // This is type-safe — wrong types won't compile
        Order order = Order.newBuilder()
                .setOrderId(orderId)
                .setCustomerId(customerId)
                .setProductId(productId)
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                .build();

        String key = orderId;

        CompletableFuture<SendResult<String, Order>> future =
                kafkaTemplate.send("order-events", key, order);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println(" Order sent: topic=" + result.getRecordMetadata().topic()
                        + ", partition=" + result.getRecordMetadata().partition()
                        + ", offset=" + result.getRecordMetadata().offset());
            }
        });
    }
}

