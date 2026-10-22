package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Set<String> roles;
    private AuthProviderType authProvider;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Long linkedReferenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}