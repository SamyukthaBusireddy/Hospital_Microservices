package com.hospital.doctors.controller;

import com.hospital.doctors.dto.DoctorDTO;
import com.hospital.doctors.dto.GlobalResponseDto;
import com.hospital.doctors.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO savedDoctor = doctorService.createNewDoctor(doctorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    // GET /api/doctors
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors(@RequestParam(required = false) String sortBy) {

        List<DoctorDTO> doctors = doctorService.getAllDoctors(sortBy);
        return ResponseEntity.ok(doctors);
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {

        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO doctorDTO) {

        DoctorDTO updatedDoctor = doctorService.updateDoctorById(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    // DELETE /api/doctors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);
        GlobalResponseDto body=new GlobalResponseDto();
        body.setMsg("Successfully Deleted");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}