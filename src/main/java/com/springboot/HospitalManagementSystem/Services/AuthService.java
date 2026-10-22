package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.AuthResponse;
import com.springboot.HospitalManagementSystem.Dtos.LoginRequest;
import com.springboot.HospitalManagementSystem.Dtos.RegisterRequest;
import com.springboot.HospitalManagementSystem.Dtos.StaffRegistrationRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse registerStaff(StaffRegistrationRequest request);

    AuthResponse login(LoginRequest request);
}