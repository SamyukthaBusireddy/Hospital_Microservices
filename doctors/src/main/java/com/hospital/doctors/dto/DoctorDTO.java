package com.hospital.doctors.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long id;

    @NotBlank(message = "Doctor name cannot be blank")
    @Size(min = 3, max = 50, message = "Doctor name should be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Specialization cannot be blank")
    private String specialization;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be a valid email")
    private String email;
}