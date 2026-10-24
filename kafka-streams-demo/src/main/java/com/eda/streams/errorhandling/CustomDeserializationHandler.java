package com.eda.streams.errorhandling;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Custom Deserialization Exception Handler.
 *
 * Called when bytes from a Kafka topic CANNOT be deserialized into a Java object.
 * (e.g., wrong serde type, corrupted data, schema mismatch)
 *
 * Strategy:
 *   - Skip bad records and continue processing
 *   - But if error count exceeds MAX_ERRORS → FAIL (something is fundamentally wrong)
 *
 * Register in application.properties:
 *   spring.kafka.streams.properties[default.deserialization.exception.handler]=\
 *     com.eda.streams.errorhandling.CustomDeserializationHandler
 */
public class CustomDeserializationHandler implements DeserializationExceptionHandler {

    private long maxErrors;
    private static final AtomicLong errorCount = new AtomicLong(0);

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext context,
                                                 ConsumerRecord<byte[], byte[]> record,
                                                 Exception exception) {

        long errors = errorCount.incrementAndGet();

        // STRATEGY: skip bad records, if limit not crossed
        if(errors > maxErrors) {
            return DeserializationHandlerResponse.FAIL;
        }

        return DeserializationHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // we can read or update stream config properties here if needed
        Object value = configs.get("stream.max.deserialization.errors");
        maxErrors = Long.parseLong(value.toString());
        if (value != null) {
            maxErrors = Long.parseLong(value.toString());
        } else {
            maxErrors = 100;
        }
    }

}

