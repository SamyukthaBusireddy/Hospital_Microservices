package com.hospital.billings.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingDTO {

    private Long id;

    @NotNull(message = "Billing amount cannot be null")
    @Positive(message = "Billing amount must be positive")
    @Digits(
            integer = 8,
            fraction = 2,
            message = "Billing amount should have maximum 8 integer digits and 2 decimal places"
    )
    private Double amount;

    @NotBlank(message = "Billing status cannot be blank")
    private String status;

    @NotNull(message = "Billing date cannot be null")
    private LocalDateTime billingDate;

    @NotNull(message = "Appointment ID cannot be null")
    private Long appointmentId;
}