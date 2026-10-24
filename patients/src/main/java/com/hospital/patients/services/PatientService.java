package com.hospital.patients.services;

import com.hospital.patients.dto.PatientDTO;
import com.hospital.patients.entities.PatientEntity;
import com.hospital.patients.exceptions.ResourceNotFoundException;
import com.hospital.patients.reposiories.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }


    public List<PatientDTO> getAllPatient(String sortBy){
        List<PatientEntity> allPatients;
        if(sortBy != null){
            allPatients = patientRepository.findAll(Sort.by(sortBy));
        }
        else {
            allPatients = patientRepository.findAll();
        }
        return allPatients.stream().
                map(patientEntity -> modelMapper.map(patientEntity, PatientDTO.class)).
                collect(Collectors.toList());

    }

    public Optional<PatientDTO> getPatientById(Long Id){
        isExistsByDoctorId(Id);
        return patientRepository.findById(Id).map(
                patientEntity -> modelMapper.map(patientEntity,PatientDTO.class)
        );
    }

    public void isExistsByDoctorId(Long Id) {
        boolean exists = patientRepository.existsById(Id);
        if (!exists) {
            throw new ResourceNotFoundException(
                    "Patient not found with id: " + Id
            );
        }
    }

    public PatientDTO createNewPatient(PatientDTO Patient) {
        PatientEntity toSaveEntity = modelMapper.map(Patient, PatientEntity.class);
        PatientEntity savedPatient = patientRepository.save(toSaveEntity);
        return modelMapper.map(savedPatient, PatientDTO.class);
    }

    public Optional<PatientDTO> updatePatientById(Long Id,PatientDTO Patient){

        isExistsByDoctorId(Id);
        PatientEntity toSaveEntity = modelMapper.map(Patient, PatientEntity.class);
        toSaveEntity.setId(Id);
        PatientEntity savedPatient = patientRepository.save(toSaveEntity);
        return patientRepository.findById(savedPatient.getId()).map(
                patientEntity -> modelMapper.map(patientEntity,PatientDTO.class)
        );
    }

    public boolean deletePatientById(Long Id){
        isExistsByDoctorId(Id);
        patientRepository.deleteById(Id);
        return true;
    }
}
