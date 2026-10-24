package com.hospital.billings.controller;


import com.hospital.billings.dto.BillingDTO;
import com.hospital.billings.dto.BillingResponseDTO;
import com.hospital.billings.dto.GlobalResponseDto;
import com.hospital.billings.services.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    private final BillingService billingService;
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<BillingDTO> createBilling(@Valid @RequestBody BillingDTO billingDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createNewBilling(billingDTO));
    }

//    @GetMapping
//    public ResponseEntity<List<BillingDTO>> getAllBillings(@RequestParam(required = false) String sortBy) {
//        return ResponseEntity.ok(billingService.getAllBillings(sortBy));
//    }
    @GetMapping
    public ResponseEntity<List<BillingResponseDTO>> getAllBillings(@RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(billingService.getAllBillings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingDTO> getBillingById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingDTO> updateBilling(@PathVariable Long id,
                                                    @Valid @RequestBody BillingDTO billingDTO) {
        return ResponseEntity.ok(billingService.updateBillingById(id, billingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseDto> deleteBilling(@PathVariable Long id) {
        billingService.deleteBillingById(id);
        GlobalResponseDto response = new GlobalResponseDto();
        response.setMsg("Billing successfully deleted");
        return ResponseEntity.ok(response);
    }
}
