package com.hospital.appointments.clients;

import com.hospital.appointments.advices.ApiResponse;
import com.hospital.appointments.advices.GlobalResponseHandler;
import com.hospital.appointments.advices.clientResponse;
import com.hospital.appointments.dto.GlobalResponseDto;
import com.hospital.appointments.dto.PatientDTO;
import com.hospital.appointments.dto.clientResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "Patient-Service")
public interface PatientClient {

    @PostMapping("/api/patients")
    clientResponseDTO<PatientDTO> createPatient(
            @Valid @RequestBody PatientDTO patientDTO
    );

    @GetMapping("/api/patients")
    ResponseEntity<List<PatientDTO>> getAllPatients(
            @RequestParam(required = false) String sortBy
    );

    @GetMapping("/api/patients/{id}")
    clientResponseDTO<PatientDTO> getPatientById(
            @PathVariable("id") Long id
    );

    @PutMapping("/api/patients/{id}")
    clientResponseDTO<Optional<PatientDTO>> updatePatient(
            @PathVariable("id") Long id,
            @Valid @RequestBody PatientDTO patientDTO
    );

    @DeleteMapping("/api/patients/{id}")
    clientResponseDTO<GlobalResponseDto> deletePatient(
            @PathVariable("id") Long id
    );
}