package com.hospital.prescription.services;

import com.hospital.prescription.dto.PrescriptionDTO;
import com.hospital.prescription.entities.PrescriptionEntity;
import com.hospital.prescription.exceptions.ResourceNotFoundException;
import com.hospital.prescription.reposiories.PrescriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ModelMapper modelMapper;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                                ModelMapper modelMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.modelMapper = modelMapper;
    }

    public PrescriptionDTO getPrescriptionById(Long id) {
        return convertToDTO(findPrescription(id));
    }

    public List<PrescriptionDTO> getAllPrescriptions(String sortBy) {
        List<PrescriptionEntity> prescriptions = sortBy == null
                ? prescriptionRepository.findAll()
                : prescriptionRepository.findAll(Sort.by(sortBy));
        return prescriptions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PrescriptionDTO createNewPrescription(PrescriptionDTO prescriptionDTO) {
        PrescriptionEntity prescription = modelMapper.map(prescriptionDTO, PrescriptionEntity.class);
//        prescription.setAppointment(findAppointment(prescriptionDTO.getAppointmentId()));
        return convertToDTO(prescriptionRepository.save(prescription));
    }

    public PrescriptionDTO updatePrescriptionById(Long id, PrescriptionDTO prescriptionDTO) {
        findPrescription(id);
        PrescriptionEntity prescription = modelMapper.map(prescriptionDTO, PrescriptionEntity.class);
        prescription.setId(id);
//        prescription.setAppointment(findAppointment(prescriptionDTO.getAppointmentId()));
        return convertToDTO(prescriptionRepository.save(prescription));
    }

    public void deletePrescriptionById(Long id) {
        findPrescription(id);
        prescriptionRepository.deleteById(id);
    }

    private PrescriptionEntity findPrescription(Long id) {
        return prescriptionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Prescription not found with id: " + id));
    }

//    private AppointmentEntity findAppointment(Long id) {
//        return appointmentRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Appointment not found with id: " + id));
//    }

    private PrescriptionDTO convertToDTO(PrescriptionEntity prescription) {
        PrescriptionDTO dto = modelMapper.map(prescription, PrescriptionDTO.class);
//        if (prescription.getAppointment() != null) {
//            dto.setAppointmentId(prescription.getAppointment().getId());
//        }
        return dto;
    }
}
