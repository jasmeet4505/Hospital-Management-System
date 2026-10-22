package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.GenderType;
import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private GenderType gender;
    private LocalDate dateOfBirth;
    private String address;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private RecordStatusType status;
    private MedicalRecordResponse medicalRecord;
    private InsuranceResponse insurance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}