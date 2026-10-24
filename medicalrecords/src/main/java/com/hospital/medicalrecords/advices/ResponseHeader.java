package com.hospital.medicalrecords.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ResponseHeader {
    private LocalDateTime timestamp;
    private String transactionId;
    private HttpStatus statusCode;

    public ResponseHeader(HttpStatus statusCode) {
        this.timestamp = LocalDateTime.now();
        this.transactionId = UUID.randomUUID().toString();
        this.statusCode = statusCode;
    }
}