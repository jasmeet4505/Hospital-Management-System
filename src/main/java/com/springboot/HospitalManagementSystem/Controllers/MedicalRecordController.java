package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordRequest;
import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordResponse;
import com.springboot.HospitalManagementSystem.Services.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('VIEW_PATIENT_RECORD', 'VIEW_OWN_RECORD', 'MANAGE_USERS')")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VIEW_PATIENT_RECORD', 'MANAGE_USERS')")
    public ResponseEntity<Page<MedicalRecordResponse>> getAllMedicalRecords(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords(pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PATIENT_RECORD')")
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}