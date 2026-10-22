package com.springboot.HospitalManagementSystem.Controllers;

import com.springboot.HospitalManagementSystem.Dtos.AuthResponse;
import com.springboot.HospitalManagementSystem.Dtos.StaffRegistrationRequest;
import com.springboot.HospitalManagementSystem.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @PostMapping("/staff/register")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<AuthResponse> registerStaff(@Valid @RequestBody StaffRegistrationRequest request) {
        return new ResponseEntity<>(authService.registerStaff(request), HttpStatus.CREATED);
    }
}