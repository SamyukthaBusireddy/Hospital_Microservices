package com.hospital.doctors.services;

import com.hospital.doctors.dto.DoctorDTO;
import com.hospital.doctors.entities.DoctorEntity;
import com.hospital.doctors.exceptions.ResourceNotFoundException;
import com.hospital.doctors.reposiories.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private Optional<DoctorEntity> doc = Optional.of(new DoctorEntity());

    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    public DoctorDTO getDoctorById(Long id) {
        isExistsByDoctorId(id);
        System.out.println( "----ID----" + id +"   "+ doc.toString());
        doc = doctorRepository.findById(id);
        System.out.println( "----ID----" + id +"   "+ doc.toString());
        return doc
                .map(doctorEntity -> modelMapper.map(doctorEntity, DoctorDTO.class))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + id));
    }


    public List<DoctorDTO> getAllDoctors(String sortBy) {
        List<DoctorEntity> doctorEntities;
        // Sorting logic
        if (sortBy != null) {
            doctorEntities = doctorRepository.findAll(Sort.by(sortBy));
        } else {
            doctorEntities = doctorRepository.findAll();
        }
        return doctorEntities.stream()
                .map(doctorEntity -> modelMapper.map(doctorEntity, DoctorDTO.class))
                .collect(Collectors.toList());
    }


    public DoctorDTO createNewDoctor(DoctorDTO inputDoctor) {
        DoctorEntity toSaveEntity = modelMapper.map(inputDoctor, DoctorEntity.class);
        DoctorEntity savedDoctorEntity = doctorRepository.save(toSaveEntity);
        return modelMapper.map(savedDoctorEntity, DoctorDTO.class);
    }


    public DoctorDTO updateDoctorById(Long doctorId, DoctorDTO doctorDTO) {
        isExistsByDoctorId(doctorId);
        DoctorEntity doctorEntity = modelMapper.map(doctorDTO, DoctorEntity.class);
        doctorEntity.setId(doctorId);
        DoctorEntity savedDoctorEntity = doctorRepository.save(doctorEntity);
        return modelMapper.map(savedDoctorEntity, DoctorDTO.class);
    }


    public void isExistsByDoctorId(Long doctorId) {
        boolean exists = doctorRepository.existsById(doctorId);
        if (!exists) {
            throw new ResourceNotFoundException(
                    "Doctor not found with id: " + doctorId
            );
        }
    }


    public boolean deleteDoctorById(Long doctorId) {
        isExistsByDoctorId(doctorId);
        doctorRepository.deleteById(doctorId);
        return true;
    }


    public DoctorDTO updatePartialDoctorById(
            Long doctorId,
            Map<String, Object> updates) {

        isExistsByDoctorId(doctorId);

        DoctorEntity doctorEntity = doctorRepository.findById(doctorId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(DoctorEntity.class, field);

            if (fieldToBeUpdated != null) {
                fieldToBeUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdated, doctorEntity, value);
            }
        });

        return modelMapper.map(
                doctorRepository.save(doctorEntity),
                DoctorDTO.class
        );
    }
}
