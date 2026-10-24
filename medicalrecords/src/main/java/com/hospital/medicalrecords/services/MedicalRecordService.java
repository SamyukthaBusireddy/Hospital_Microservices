package com.hospital.medicalrecords.services;

import com.hospital.medicalrecords.dto.MedicalRecordDTO;
import com.hospital.medicalrecords.entities.MedicalRecordEntity;
import com.hospital.medicalrecords.exceptions.ResourceNotFoundException;
import com.hospital.medicalrecords.reposiories.MedicalRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
//    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                ModelMapper modelMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.modelMapper = modelMapper;
    }

    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return convertToDTO(findMedicalRecord(id));
    }

    public List<MedicalRecordDTO> getAllMedicalRecords(String sortBy) {
        List<MedicalRecordEntity> records = sortBy == null
                ? medicalRecordRepository.findAll()
                : medicalRecordRepository.findAll(Sort.by(sortBy));
        return records.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MedicalRecordDTO createNewMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordEntity record = modelMapper.map(medicalRecordDTO, MedicalRecordEntity.class);
//        record.setPatient(findPatient(medicalRecordDTO.getPatientId()));
        return convertToDTO(medicalRecordRepository.save(record));
    }

    public MedicalRecordDTO updateMedicalRecordById(Long id, MedicalRecordDTO medicalRecordDTO) {
        findMedicalRecord(id);
        MedicalRecordEntity record = modelMapper.map(medicalRecordDTO, MedicalRecordEntity.class);
        record.setId(id);
//        record.setPatient(findPatient(medicalRecordDTO.getPatientId()));
        return convertToDTO(medicalRecordRepository.save(record));
    }

    public void deleteMedicalRecordById(Long id) {
        findMedicalRecord(id);
        medicalRecordRepository.deleteById(id);
    }

    private MedicalRecordEntity findMedicalRecord(Long id) {
        return medicalRecordRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Medical record not found with id: " + id));
    }

//    private PatientEntity findPatient(Long id) {
//        return patientRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Patient not found with id: " + id));
//    }

    private MedicalRecordDTO convertToDTO(MedicalRecordEntity record) {
        MedicalRecordDTO dto = modelMapper.map(record, MedicalRecordDTO.class);
//        if (record.getPatient() != null) {
//            dto.setPatientId(record.getPatient().getId());
//        }
        return dto;
    }
}
