package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.BloodGroupType;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordRequest {

    private BloodGroupType bloodGroup;

    private String allergies;

    private String chronicConditions;

    private String currentMedications;

    private String pastSurgeries;

    private String familyMedicalHistory;

    @DecimalMin(value = "0.0", inclusive = false, message = "Height must be a positive value")
    private Double heightCm;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be a positive value")
    private Double weightKg;

    private String notes;
}