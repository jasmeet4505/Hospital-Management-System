package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.PatientRequest;
import com.springboot.HospitalManagementSystem.Dtos.PatientResponse;
import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    PatientResponse getPatientById(Long id);

    Page<PatientResponse> getAllPatients(Pageable pageable);

    Page<PatientResponse> getPatientsByStatus(RecordStatusType status, Pageable pageable);

    Page<PatientResponse> searchPatientsByName(String name, Pageable pageable);

    PatientResponse updatePatient(Long id, PatientRequest request);

    void deletePatient(Long id);

    void softDeletePatient(Long id);
}