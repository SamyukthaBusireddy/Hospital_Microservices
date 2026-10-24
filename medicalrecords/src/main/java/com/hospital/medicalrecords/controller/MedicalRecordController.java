package com.hospital.medicalrecords.controller;

import com.hospital.medicalrecords.dto.GlobalResponseDto;
import com.hospital.medicalrecords.dto.MedicalRecordDTO;
import com.hospital.medicalrecords.services.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(
            @Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicalRecordService.createNewMedicalRecord(medicalRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords(
            @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords(sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.ok(
                medicalRecordService.updateMedicalRecordById(id, medicalRecordDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecordById(id);
        GlobalResponseDto response = new GlobalResponseDto();
        response.setMsg("Medical record successfully deleted");
        return ResponseEntity.ok(response);
    }
}
