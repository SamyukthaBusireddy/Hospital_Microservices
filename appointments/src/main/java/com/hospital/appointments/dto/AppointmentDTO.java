package com.hospital.appointments.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotNull(message = "Appointment date cannot be null")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Appointment reason cannot be blank")
    @Size(
            max = 500,
            message = "Appointment reason cannot exceed 500 characters"
    )
    private String reason;

    @NotBlank(message = "Appointment status cannot be blank")
    private String status;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

//    private Long prescriptionId;
//
//    private Long billingId;
}