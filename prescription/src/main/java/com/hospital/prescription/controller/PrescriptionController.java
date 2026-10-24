package com.hospital.prescription.controller;

import com.hospital.prescription.dto.GlobalResponseDto;
import com.hospital.prescription.dto.PrescriptionDTO;
import com.hospital.prescription.services.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDTO> createPrescription(@Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prescriptionService.createNewPrescription(prescriptionDTO));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions(
            @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions(sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        return ResponseEntity.ok(prescriptionService.updatePrescriptionById(id, prescriptionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescriptionById(id);
        GlobalResponseDto response = new GlobalResponseDto();
        response.setMsg("Prescription successfully deleted");
        return ResponseEntity.ok(response);
    }
}
