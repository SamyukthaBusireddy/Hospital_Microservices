package com.eda.streams.config;

import com.eda.streams.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStreamTopology {

    private static final ObjectMapper objectMapper = new ObjectMapper();

   //@Bean
    public KStream<String, String> orderCountTopology(StreamsBuilder builder) {

        // SOURCE: read from "order-events" as raw JSON strings ──
        KStream<String, String> orderStream = builder.stream("order-events",
                Consumed.with(Serdes.String(), Serdes.String()));

        // FILTER: only orders with totalAmount > 100 ──
        KStream<String, String> highValueOrders = orderStream.filter((key, value) -> {
            try {
                Order order = objectMapper.readValue(value, Order.class);
                return order.getTotalAmount() != null && order.getTotalAmount() > 100;
            } catch (Exception e) {
                return false;
            }
        });

        // Re-key by productId
        // This will trigger a REPARTITION topic
        KStream<String, String> productKeyedOrders =
                highValueOrders.selectKey((oldKey, value) -> {
                    try {
                        Order order = objectMapper.readValue(value, Order.class);
                        return order.getProductId();
                    } catch (Exception e) {
                        return "UNKNOWN";
                    }
                });

        //RocksDB store name
        KTable<String, Long> productCounts =
                productKeyedOrders
                        .groupByKey(Grouped.with(
                                Serdes.String(),
                                Serdes.String()))
                        .count(Materialized.as("product-count-store"));

        //output topic
        productCounts
                .toStream()
                .mapValues(String::valueOf)
                .to("product-counts",
                        Produced.with(
                                Serdes.String(),
                                Serdes.String()));

        return orderStream;
    }
}

