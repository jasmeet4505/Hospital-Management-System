package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.SpecialtyRequest;
import com.springboot.HospitalManagementSystem.Dtos.SpecialtyResponse;
import com.springboot.HospitalManagementSystem.Services.SpecialtyService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_DEPARTMENTS')")
    public ResponseEntity<SpecialtyResponse> createSpecialty(@Valid @RequestBody SpecialtyRequest request) {
        return new ResponseEntity<>(specialtyService.createSpecialty(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DEPARTMENTS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT', 'MANAGE_USERS')")
    public ResponseEntity<SpecialtyResponse> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_DEPARTMENTS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT', 'MANAGE_USERS')")
    public ResponseEntity<Page<SpecialtyResponse>> getAllSpecialties(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(specialtyService.getAllSpecialties(pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_DEPARTMENTS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT', 'MANAGE_USERS')")
    public ResponseEntity<Page<SpecialtyResponse>> searchSpecialties(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(specialtyService.searchSpecialtiesByName(name, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_DEPARTMENTS')")
    public ResponseEntity<SpecialtyResponse> updateSpecialty(
            @PathVariable Long id,
            @Valid @RequestBody SpecialtyRequest request) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_DEPARTMENTS')")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }
}