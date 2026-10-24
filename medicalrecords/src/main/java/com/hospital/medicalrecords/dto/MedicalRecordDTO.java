package com.hospital.medicalrecords.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class MedicalRecordDTO {

    private Long id;

    @NotBlank(message = "Diagnosis cannot be blank")
    @Size(
            max = 500,
            message = "Diagnosis cannot exceed 500 characters"
    )
    private String diagnosis;

    @NotBlank(message = "Treatment cannot be blank")
    @Size(
            max = 1000,
            message = "Treatment cannot exceed 1000 characters"
    )
    private String treatment;

    @NotNull(message = "Record date cannot be null")
    @PastOrPresent(message = "Record date cannot be in the future")
    private LocalDateTime recordDate;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;
}