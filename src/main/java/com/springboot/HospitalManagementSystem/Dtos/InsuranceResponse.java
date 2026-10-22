package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.InsurancePlanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceResponse {

    private Long id;
    private String providerName;
    private String policyNumber;
    private InsurancePlanType planType;
    private BigDecimal coverageAmount;
    private BigDecimal premiumAmount;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private Boolean active;
}