package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.DoctorRequest;
import com.springboot.HospitalManagementSystem.Dtos.DoctorResponse;
import com.springboot.HospitalManagementSystem.Services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return new ResponseEntity<>(doctorService.createDoctor(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT')")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT')")
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE')")
    public ResponseEntity<Page<DoctorResponse>> getDoctorsByDepartment(
            @PathVariable Long departmentId,
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(doctorService.getDoctorsByDepartment(departmentId, pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_PATIENT_RECORD', 'VIEW_SCHEDULE', 'BOOK_APPOINTMENT')")
    public ResponseEntity<Page<DoctorResponse>> searchDoctors(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(doctorService.searchDoctorsByName(name, pageable));
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyAuthority('BOOK_APPOINTMENT', 'MANAGE_USERS', 'VIEW_SCHEDULE')")
    public ResponseEntity<Page<DoctorResponse>> findAvailableDoctors(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAvailableDoctors(startTime, endTime, pageable));
    }

    @GetMapping("/available/specialty/{specialtyId}")
    @PreAuthorize("hasAnyAuthority('BOOK_APPOINTMENT', 'MANAGE_USERS', 'VIEW_SCHEDULE')")
    public ResponseEntity<Page<DoctorResponse>> findAvailableDoctorsBySpecialty(
            @PathVariable Long specialtyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAvailableDoctorsBySpecialty(specialtyId, startTime, endTime, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}