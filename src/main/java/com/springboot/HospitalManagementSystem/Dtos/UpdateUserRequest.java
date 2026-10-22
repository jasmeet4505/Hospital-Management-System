package com.springboot.HospitalManagementSystem.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 150, message = "Full name must not exceed 150 characters")
    private String fullName;

    private Set<String> roleNames;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;

    @NotNull(message = "Account non locked flag is required")
    private Boolean accountNonLocked;

    private Long linkedReferenceId;
}