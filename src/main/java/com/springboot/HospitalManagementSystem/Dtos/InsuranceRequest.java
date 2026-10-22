package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.InsurancePlanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceRequest {

    @NotBlank(message = "Provider name is required")
    @Size(max = 150, message = "Provider name must not exceed 150 characters")
    private String providerName;

    @NotBlank(message = "Policy number is required")
    @Size(max = 100, message = "Policy number must not exceed 100 characters")
    private String policyNumber;

    @NotNull(message = "Plan type is required")
    private InsurancePlanType planType;

    @DecimalMin(value = "0.0", inclusive = true, message = "Coverage amount must not be negative")
    private BigDecimal coverageAmount;

    @DecimalMin(value = "0.0", inclusive = true, message = "Premium amount must not be negative")
    private BigDecimal premiumAmount;

    @NotNull(message = "Valid from date is required")
    private LocalDate validFrom;

    @NotNull(message = "Valid until date is required")
    @FutureOrPresent(message = "Valid until date must be in the present or future")
    private LocalDate validUntil;

    @NotNull(message = "Active flag is required")
    private Boolean active;
}