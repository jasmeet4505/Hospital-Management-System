package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.DoctorAvailabilityStatusType;
import com.springboot.HospitalManagementSystem.Entities.Type.GenderType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 80, message = "First name must not exceed 80 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 80, message = "Last name must not exceed 80 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{7,20}$", message = "Phone number must be a valid format")
    private String phoneNumber;

    private GenderType gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "License number is required")
    @Size(max = 100, message = "License number must not exceed 100 characters")
    private String licenseNumber;

    @Min(value = 0, message = "Years of experience must not be negative")
    private Integer yearsOfExperience;

    @DecimalMin(value = "0.0", inclusive = true, message = "Consultation fee must not be negative")
    private BigDecimal consultationFee;

    @NotNull(message = "Availability status is required")
    private DoctorAvailabilityStatusType availabilityStatus;

    @NotNull(message = "Department id is required")
    private Long departmentId;

    @NotEmpty(message = "At least one specialty must be assigned")
    private Set<Long> specialtyIds;
}