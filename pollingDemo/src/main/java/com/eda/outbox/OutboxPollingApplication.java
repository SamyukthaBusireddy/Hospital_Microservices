package com.eda.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableScheduling — required for @Scheduled to work.
 * Without this, the OutboxPoller's @Scheduled method will never execute.
 */
@SpringBootApplication
@EnableScheduling
public class OutboxPollingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxPollingApplication.class, args);
    }
}

