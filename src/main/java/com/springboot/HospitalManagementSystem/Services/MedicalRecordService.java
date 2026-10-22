package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordRequest;
import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordResponse;
import com.springboot.HospitalManagementSystem.Entities.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {

    MedicalRecordResponse getMedicalRecordById(Long id);

    Page<MedicalRecordResponse> getAllMedicalRecords(Pageable pageable);

    MedicalRecordResponse updateMedicalRecord(Long id, MedicalRecordRequest request);

    void deleteMedicalRecord(Long id);

    MedicalRecord buildFromRequest(MedicalRecordRequest request);

    void applyRequest(MedicalRecord medicalRecord, MedicalRecordRequest request);
}