package com.hospital.patients.controller;

import com.hospital.patients.dto.GlobalResponseDto;
import com.hospital.patients.dto.PatientDTO;
import com.hospital.patients.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO patient = patientService.createNewPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    // GET /api/doctors
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients(@RequestParam(required = false) String sortBy) {

        List<PatientDTO> patients = patientService.getAllPatient(sortBy);
        return ResponseEntity.ok(patients);
    }

    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> getPatientById(@PathVariable Long id) {

        Optional<PatientDTO> doctor = patientService.getPatientById(id);
        return ResponseEntity.ok(doctor);
    }

    // PUT /api/doctors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> updateDoctor(@PathVariable Long id, @Valid @RequestBody PatientDTO PatientDTO) {

        Optional<PatientDTO> updatedPatient = patientService.updatePatientById(id, PatientDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    // DELETE /api/doctors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deletePatient(@PathVariable Long id) {
        patientService.deletePatientById(id);
        GlobalResponseDto body=new GlobalResponseDto();
        body.setMsg("Successfully Deleted");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}