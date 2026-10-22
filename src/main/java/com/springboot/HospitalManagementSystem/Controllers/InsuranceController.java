package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.InsuranceRequest;
import com.springboot.HospitalManagementSystem.Dtos.InsuranceResponse;
import com.springboot.HospitalManagementSystem.Services.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<InsuranceResponse> createInsurance(@Valid @RequestBody InsuranceRequest request) {
        return new ResponseEntity<>(insuranceService.createInsurance(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD')")
    public ResponseEntity<InsuranceResponse> getInsuranceById(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceService.getInsuranceById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD')")
    public ResponseEntity<Page<InsuranceResponse>> getAllInsurances(
            @PageableDefault(size = 10, sort = "providerName") Pageable pageable) {
        return ResponseEntity.ok(insuranceService.getAllInsurances(pageable));
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD')")
    public ResponseEntity<Page<InsuranceResponse>> getActiveInsurances(
            @PageableDefault(size = 10, sort = "providerName") Pageable pageable) {
        return ResponseEntity.ok(insuranceService.getActiveInsurances(pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<InsuranceResponse> updateInsurance(
            @PathVariable Long id,
            @Valid @RequestBody InsuranceRequest request) {
        return ResponseEntity.ok(insuranceService.updateInsurance(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }
}