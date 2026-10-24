package com.hospital.appointments.services;

import com.hospital.appointments.clients.DoctorClient;
import com.hospital.appointments.clients.PatientClient;
import com.hospital.appointments.dto.*;
import com.hospital.appointments.entities.AppointmentEntity;
import com.hospital.appointments.exceptions.ResourceNotFoundException;
import com.hospital.appointments.exceptions.ServerBusyException;
import com.hospital.appointments.reposiories.AppointmentRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
//    private final PatientRepository patientRepository;
//    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    @Autowired
    patientService patientService;
    @Autowired
    doctorService doctorService;
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            ModelMapper modelMapper) {


        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }


    // GET APPOINTMENT BY ID
    public AppointmentResponseDTO getAppointmentById(Long id) {

        isExistsByAppointmentId(id);

        AppointmentEntity appointmentEntity = appointmentRepository.findById(id).orElseThrow(() ->
                                new ResourceNotFoundException("Appointment not found with id: " + id));
        AppointmentDTO appointmentDTO = modelMapper.map(appointmentEntity, AppointmentDTO.class);
        AppointmentResponseDTO appointmentResponseDTO = modelMapper.map(appointmentDTO, AppointmentResponseDTO.class);

        long pid = appointmentDTO.getPatientId();
        PatientDTO patient = (PatientDTO)patientService.getPatientById(pid).getResponseBody().getData();
        appointmentResponseDTO.setPatient(patient);

        long did = appointmentDTO.getDoctorId();
        DoctorDTO doctor = (DoctorDTO)doctorService.getDoctorById(pid).getResponseBody().getData();
        appointmentResponseDTO.setDoctor(doctor);

//        if (appointmentEntity.getDoctor() != null) {
//            appointmentDTO.setDoctorId(
//                    appointmentEntity.getDoctor().getId());
//        }

        // Map Prescription ID
//        if (appointmentEntity.getPrescription() != null) {
//            appointmentDTO.setPrescriptionId(
//                    appointmentEntity.getPrescription().getId());
//        }

        // Map Billing ID
//        if (appointmentEntity.getBilling() != null) {
//            appointmentDTO.setBillingId(
//                    appointmentEntity.getBilling().getId());
//        }
        return appointmentResponseDTO;
    }

    // GET ALL APPOINTMENTS
    public List<AppointmentDTO> getAllAppointments(String sortBy) {

        List<AppointmentEntity> appointmentEntities;

        // Sorting logic
        if (sortBy != null) {
            appointmentEntities =
                    appointmentRepository.findAll(Sort.by(sortBy));
        } else {
            appointmentEntities =
                    appointmentRepository.findAll();
        }

        return appointmentEntities.stream()
                .map(appointmentEntity -> {

                    AppointmentDTO dto =
                            modelMapper.map(
                                    appointmentEntity,
                                    AppointmentDTO.class);

//                    if (appointmentEntity.getPatient() != null) {
//                        dto.setPatientId(
//                                appointmentEntity.getPatient().getId());
//                    }
//
//                    if (appointmentEntity.getDoctor() != null) {
//                        dto.setDoctorId(
//                                appointmentEntity.getDoctor().getId());
//                    }

//                    if (appointmentEntity.getPrescription() != null) {
//                        dto.setPrescriptionId(
//                                appointmentEntity.getPrescription().getId());
//                    }
//
//                    if (appointmentEntity.getBilling() != null) {
//                        dto.setBillingId(
//                                appointmentEntity.getBilling().getId());
//                    }

                    return dto;

                })
                .collect(Collectors.toList());
    }


    // CREATE APPOINTMENT
    public AppointmentDTO createNewAppointment(
            AppointmentDTO inputAppointment) {

        // Find Patient
//        PatientEntity patientEntity =
//                patientRepository.findById(
//                                inputAppointment.getPatientId())
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Patient not found with id: "
//                                                + inputAppointment.getPatientId()));
//
//        // Find Doctor
//        DoctorEntity doctorEntity =
//                doctorRepository.findById(
//                                inputAppointment.getDoctorId())
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Doctor not found with id: "
//                                                + inputAppointment.getDoctorId()));

        AppointmentEntity appointmentEntity =
                modelMapper.map(
                        inputAppointment,
                        AppointmentEntity.class);

        // Set relationships
//        appointmentEntity.setPatient(patientEntity);
//        appointmentEntity.setDoctor(doctorEntity);

        AppointmentEntity savedAppointmentEntity =
                appointmentRepository.save(appointmentEntity);

        return convertToDTO(savedAppointmentEntity);
    }


    // UPDATE APPOINTMENT
    public AppointmentDTO updateAppointmentById(
            Long appointmentId,
            AppointmentDTO appointmentDTO) {

        isExistsByAppointmentId(appointmentId);

        // Find Patient
//        PatientEntity patientEntity =
//                patientRepository.findById(
//                                appointmentDTO.getPatientId())
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Patient not found with id: "
//                                                + appointmentDTO.getPatientId()));
//
//        // Find Doctor
//        DoctorEntity doctorEntity =
//                doctorRepository.findById(
//                                appointmentDTO.getDoctorId())
//                        .orElseThrow(() ->
//                                new ResourceNotFoundException(
//                                        "Doctor not found with id: "
//                                                + appointmentDTO.getDoctorId()));

        AppointmentEntity appointmentEntity =
                modelMapper.map(
                        appointmentDTO,
                        AppointmentEntity.class);

        appointmentEntity.setId(appointmentId);

        // Set relationships
//        appointmentEntity.setPatient(patientEntity);
//        appointmentEntity.setDoctor(doctorEntity);

        AppointmentEntity savedAppointmentEntity =
                appointmentRepository.save(appointmentEntity);

        return convertToDTO(savedAppointmentEntity);
    }


    // CHECK APPOINTMENT EXISTS
    public void isExistsByAppointmentId(Long appointmentId) {

        boolean exists =
                appointmentRepository.existsById(appointmentId);

        if (!exists) {
            throw new ResourceNotFoundException(
                    "Appointment not found with id: " + appointmentId
            );
        }
    }


    // DELETE APPOINTMENT
    public boolean deleteAppointmentById(Long appointmentId) {

        isExistsByAppointmentId(appointmentId);

        appointmentRepository.deleteById(appointmentId);

        return true;
    }


    // PARTIAL UPDATE
    public AppointmentDTO updatePartialAppointmentById(
            Long appointmentId,
            Map<String, Object> updates) {

        isExistsByAppointmentId(appointmentId);

        AppointmentEntity appointmentEntity =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found with id: "
                                                + appointmentId));

        updates.forEach((field, value) -> {

            Field fieldToBeUpdated =
                    ReflectionUtils.findField(
                            AppointmentEntity.class,
                            field);

            if (fieldToBeUpdated != null) {

                fieldToBeUpdated.setAccessible(true);

                ReflectionUtils.setField(
                        fieldToBeUpdated,
                        appointmentEntity,
                        value);
            }
        });

        return convertToDTO(
                appointmentRepository.save(appointmentEntity));
    }


    // ENTITY -> DTO
    private AppointmentDTO convertToDTO(
            AppointmentEntity appointmentEntity) {

        AppointmentDTO dto =
                modelMapper.map(
                        appointmentEntity,
                        AppointmentDTO.class);

//        if (appointmentEntity.getPatient() != null) {
//            dto.setPatientId(
//                    appointmentEntity.getPatient().getId());
//        }
//
//        if (appointmentEntity.getDoctor() != null) {
//            dto.setDoctorId(
//                    appointmentEntity.getDoctor().getId());
//        }
//
//        if (appointmentEntity.getPrescription() != null) {
//            dto.setPrescriptionId(
//                    appointmentEntity.getPrescription().getId());
//        }
//
//        if (appointmentEntity.getBilling() != null) {
//            dto.setBillingId(
//                    appointmentEntity.getBilling().getId());
//        }

        return dto;
    }
}