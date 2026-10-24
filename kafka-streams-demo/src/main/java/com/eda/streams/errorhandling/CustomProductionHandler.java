package com.eda.streams.errorhandling;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

import java.util.Map;

/**
 * Custom Production Exception Handler.
 *
 * Called when:
 *   1. Java object → bytes SERIALIZATION fails (e.g., wrong output serde)
 *   2. Producing to Kafka fails (e.g., record too large, broker issue)
 *
 * This covers SINK node errors: stream.to(), repartition topics, changelog topics.
 *
 * Register in application.properties:
 *   spring.kafka.streams.properties[default.production.exception.handler]=\
 *     com.eda.streams.errorhandling.CustomProductionHandler
 */
public class CustomProductionHandler implements ProductionExceptionHandler {

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> record,
                                                      Exception exception) {

        System.err.println("═══════════════════════════════════════════════════");
        System.err.println("[PRODUCTION/SERIALIZATION ERROR]");
        System.err.println("  Topic:     " + record.topic());
        System.err.println("  Partition: " + (record.partition() != null ? record.partition() : "auto"));
        System.err.println("  Key size:  " + (record.key() != null ? record.key().length + " bytes" : "null"));
        System.err.println("  Value size:" + (record.value() != null ? record.value().length + " bytes" : "null"));
        System.err.println("  Exception: " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
        System.err.println("═══════════════════════════════════════════════════");

        // RecordTooLarge — can't fix by retrying, skip it
        if (exception.getClass().getSimpleName().contains("RecordTooLarge")) {
            System.err.println("  → Record too large. Skipping.");
            return ProductionExceptionHandlerResponse.CONTINUE;
        }

        // All other errors — fail (serialization bugs should be fixed in code)
        System.err.println("  → Unexpected production error. FAILING.");
        return ProductionExceptionHandlerResponse.FAIL;
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}

