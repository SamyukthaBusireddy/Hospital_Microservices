package com.hospital.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDTO {

    private Long id;

    @NotBlank(message = "Medication cannot be blank")
    @Size(
            max = 200,
            message = "Medication cannot exceed 200 characters"
    )
    private String medication;

    @NotBlank(message = "Dosage cannot be blank")
    @Size(
            max = 100,
            message = "Dosage cannot exceed 100 characters"
    )
    private String dosage;

    @NotBlank(message = "Instructions cannot be blank")
    @Size(
            max = 500,
            message = "Instructions cannot exceed 500 characters"
    )
    private String instructions;

    @NotNull(message = "Appointment ID cannot be null")
    private Long appointmentId;
}