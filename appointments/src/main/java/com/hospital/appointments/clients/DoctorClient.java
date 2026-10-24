package com.hospital.appointments.clients;


import com.hospital.appointments.dto.DoctorDTO;
import com.hospital.appointments.dto.GlobalResponseDto;
import com.hospital.appointments.dto.PatientDTO;
import com.hospital.appointments.dto.clientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("/api/doctors/{id}")
    clientResponseDTO<DoctorDTO> getDoctorById(@PathVariable("id") Long id);

    @GetMapping("/api/doctors")
    ResponseEntity<List<DoctorDTO>> getAllDoctors(
            @RequestParam(value = "sortBy", required = false) String sortBy
    );

    @PostMapping("/api/doctors")
    clientResponseDTO<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO);

    @PutMapping("/api/doctors/{id}")
    clientResponseDTO<Optional<DoctorDTO>> updateDoctor(
            @PathVariable("id") Long id,
            @RequestBody DoctorDTO doctorDTO
    );

    @DeleteMapping("/api/doctors/{id}")
    clientResponseDTO<GlobalResponseDto> deleteDoctor(@PathVariable("id") Long id);
}
