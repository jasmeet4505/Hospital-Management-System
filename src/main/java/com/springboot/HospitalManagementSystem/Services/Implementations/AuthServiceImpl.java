package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.AuthResponse;
import com.springboot.HospitalManagementSystem.Dtos.LoginRequest;
import com.springboot.HospitalManagementSystem.Dtos.RegisterRequest;
import com.springboot.HospitalManagementSystem.Dtos.StaffRegistrationRequest;
import com.springboot.HospitalManagementSystem.Entities.Role;
import com.springboot.HospitalManagementSystem.Entities.Type.AuthProviderType;
import com.springboot.HospitalManagementSystem.Entities.User;
import com.springboot.HospitalManagementSystem.Exceptions.DuplicateResourceException;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.RoleRepository;
import com.springboot.HospitalManagementSystem.Repositories.UserRepository;
import com.springboot.HospitalManagementSystem.Security.JwtUtil;
import com.springboot.HospitalManagementSystem.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String PATIENT_ROLE = "ROLE_PATIENT";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + request.getEmail());
        }

        Role patientRole = roleRepository.findByNameWithPrivileges(PATIENT_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Default role not found. Please ensure ROLE_PATIENT exists in the database."));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthProvider(AuthProviderType.LOCAL);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setRoles(new HashSet<>(Set.of(patientRole)));

        User saved = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return buildAuthResponse(token, saved);
    }

    @Override
    @Transactional
    public AuthResponse registerStaff(StaffRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + request.getEmail());
        }

        Role role = roleRepository.findByNameWithPrivileges(request.getRoleName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role not found with name: " + request.getRoleName()));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthProvider(AuthProviderType.LOCAL);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setRoles(new HashSet<>(Set.of(role)));

        User saved = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return buildAuthResponse(token, saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmailWithRolesAndPrivileges(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found after successful authentication"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return buildAuthResponse(token, user);
    }

    private AuthResponse buildAuthResponse(String token, User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpirationMs())
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }
}