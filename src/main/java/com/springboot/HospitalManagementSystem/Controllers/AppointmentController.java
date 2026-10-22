package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.AppointmentCancellationRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentResponse;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import com.springboot.HospitalManagementSystem.Services.AppointmentService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK_APPOINTMENT')")
    public ResponseEntity<AppointmentResponse> bookAppointment(@Valid @RequestBody AppointmentRequest request) {
        return new ResponseEntity<>(appointmentService.bookAppointment(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('BOOK_APPOINTMENT', 'VIEW_SCHEDULE', 'VIEW_PATIENT_RECORD', 'MANAGE_USERS')")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_SCHEDULE')")
    public ResponseEntity<Page<AppointmentResponse>> getAllAppointments(
            @PageableDefault(size = 10, sort = "scheduledStartTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(pageable));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyAuthority('VIEW_PATIENT_RECORD', 'MANAGE_USERS')")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByPatient(
            @PathVariable Long patientId,
            @PageableDefault(size = 10, sort = "scheduledStartTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId, pageable));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyAuthority('VIEW_SCHEDULE', 'MANAGE_USERS')")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByDoctor(
            @PathVariable Long doctorId,
            @PageableDefault(size = 10, sort = "scheduledStartTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId, pageable));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_SCHEDULE')")
    public ResponseEntity<Page<AppointmentResponse>> getAppointmentsByStatus(
            @PathVariable AppointmentStatusType status,
            @PageableDefault(size = 10, sort = "scheduledStartTime") Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS', 'VIEW_SCHEDULE')")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatusType status) {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, status));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('CANCEL_APPOINTMENT', 'MANAGE_USERS')")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentCancellationRequest request) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ANY_APPOINTMENT')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}