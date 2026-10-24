package com.hospital.billings.services;


import com.hospital.billings.dto.BillingDTO;
import com.hospital.billings.dto.BillingResponseDTO;
import com.hospital.billings.entities.BillingEntity;
import com.hospital.billings.exceptions.ResourceNotFoundException;
import com.hospital.billings.reposiories.BillingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    //implement rest client for appointment service
//    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public BillingService(BillingRepository billingRepository,
                          ModelMapper modelMapper) {
        this.billingRepository = billingRepository;
        this.modelMapper = modelMapper;
    }

    public BillingDTO getBillingById(Long id) {
        return convertToDTO(billingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Billing not found with id: " + id)));
    }

    public List<BillingDTO> getAllBillings(String sortBy) {
        List<BillingEntity> billings = sortBy == null
                ? billingRepository.findAll()
                : billingRepository.findAll(Sort.by(sortBy));
//        System.out.println(billings);
        return billings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<BillingResponseDTO> getAllBillings() {
        List<BillingEntity> billings = billingRepository.findAll();
//        System.out.println(billings);
        return billings.stream().map(billingEntity -> modelMapper.map(billingEntity, BillingResponseDTO.class)).collect(Collectors.toList());
    }

    public BillingDTO createNewBilling(BillingDTO billingDTO) {
        BillingEntity billing = modelMapper.map(billingDTO, BillingEntity.class);
        return convertToDTO(billingRepository.save(billing));
    }

    public BillingDTO updateBillingById(Long id, BillingDTO billingDTO) {
        findBilling(id);
        BillingEntity billing = modelMapper.map(billingDTO, BillingEntity.class);
        billing.setId(id);
        return convertToDTO(billingRepository.save(billing));
    }

    public void deleteBillingById(Long id) {
        findBilling(id);
        billingRepository.deleteById(id);
    }

    private BillingEntity findBilling(Long id) {
        return billingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Billing not found with id: " + id));
    }

    private BillingDTO convertToDTO(BillingEntity billing) {
        BillingDTO dto = modelMapper.map(billing, BillingDTO.class);
        return dto;
    }
}
