package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordResponse {

    private Long id;
    private BloodGroupType bloodGroup;
    private String allergies;
    private String chronicConditions;
    private String currentMedications;
    private String pastSurgeries;
    private String familyMedicalHistory;
    private Double heightCm;
    private Double weightKg;
    private String notes;
}