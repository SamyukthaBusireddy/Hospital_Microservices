package com.eda.streams.errorhandling;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

import java.util.Map;

/**
 * Always skip failed records — equivalent of LogAndContinueExceptionHandler
 * but for production/serialization errors.
 *
 * WARNING: Bad records are LOST forever. Use only when you can afford data loss.
 *
 * Register in application.properties:
 *   spring.kafka.streams.properties[default.production.exception.handler]=\
 *     com.eda.streams.errorhandling.AlwaysContinueProductionHandler
 */
public class AlwaysContinueProductionHandler implements ProductionExceptionHandler {

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> record,
                                                      Exception exception) {

        System.err.println("[PRODUCTION ERROR — SKIPPING]");
        System.err.println("  Topic:     " + record.topic());
        System.err.println("  Exception: " + exception.getClass().getSimpleName()
                                            + ": " + exception.getMessage());

        return ProductionExceptionHandlerResponse.CONTINUE;   // ← ALWAYS skip
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}

