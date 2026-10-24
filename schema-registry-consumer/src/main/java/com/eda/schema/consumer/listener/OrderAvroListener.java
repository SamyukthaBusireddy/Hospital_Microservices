package com.eda.schema.consumer.listener;

import com.eda.schema.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderAvroListener {

    @KafkaListener(topics = "order-events")
    public void consumeOrder(ConsumerRecord<String, Order> record) {

        Order order = record.value();
        System.out.println("   orderId   : " + order.getOrderId());
    }
}

