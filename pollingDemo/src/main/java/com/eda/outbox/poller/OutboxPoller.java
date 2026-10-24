package com.eda.outbox.poller;

import com.eda.outbox.entity.OutboxEvent;
import com.eda.outbox.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
public class OutboxPoller {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${outbox.polling.batch-size}")
    private int batchSize;

    @Scheduled(fixedRateString = "${outbox.polling.interval-ms}")
    @Transactional
    public void pollAndPublish() {

        // Step 1: Fetch unpublished events
        List<OutboxEvent> events = outboxRepository.findUnpublishedEvents(batchSize);

        if (events.isEmpty()) {
            return;
        }

        for (OutboxEvent event : events) {
            try {

                String topic = event.getAggregateType().toLowerCase() + "-events";

                kafkaTemplate.send(topic, event.getAggregateId(), event.getPayload())
                        .get();

                event.setPublished(true);
                event.setPublishedAt(Instant.now());
                outboxRepository.save(event);

            } catch (Exception e) {
                // BREAK to preserve ordering!
                // If event 1 fails, we must NOT skip to event 2
                break;
            }
        }
    }
}

