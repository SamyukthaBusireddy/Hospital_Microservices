package com.hospital.appointments.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {

    private Long id;

    @NotBlank(message = "Patient name cannot be blank")
    @Size(
            min = 3,
            max = 50,
            message = "Patient name should be between 3 and 50 characters"
    )
    private String name;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotBlank(message = "Contact cannot be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Contact must contain exactly 10 digits"
    )
    private String contact;

    @NotBlank(message = "Address cannot be blank")
    @Size(
            max = 500,
            message = "Address cannot exceed 500 characters"
    )
    private String address;

    private LocalDateTime createdAt;
}