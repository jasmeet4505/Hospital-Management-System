package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordRequest;
import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordResponse;
import com.springboot.HospitalManagementSystem.Entities.MedicalRecord;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.MedicalRecordRepository;
import com.springboot.HospitalManagementSystem.Services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordResponse getMedicalRecordById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id));
        return mapToResponse(medicalRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponse> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public MedicalRecordResponse updateMedicalRecord(Long id, MedicalRecordRequest request) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id));
        applyRequest(medicalRecord, request);
        MedicalRecord updated = medicalRecordRepository.save(medicalRecord);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteMedicalRecord(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id));
        medicalRecordRepository.delete(medicalRecord);
    }

    @Override
    public MedicalRecord buildFromRequest(MedicalRecordRequest request) {
        MedicalRecord medicalRecord = new MedicalRecord();
        applyRequest(medicalRecord, request);
        return medicalRecord;
    }

    @Override
    public void applyRequest(MedicalRecord medicalRecord, MedicalRecordRequest request) {
        medicalRecord.setBloodGroup(request.getBloodGroup());
        medicalRecord.setAllergies(request.getAllergies());
        medicalRecord.setChronicConditions(request.getChronicConditions());
        medicalRecord.setCurrentMedications(request.getCurrentMedications());
        medicalRecord.setPastSurgeries(request.getPastSurgeries());
        medicalRecord.setFamilyMedicalHistory(request.getFamilyMedicalHistory());
        medicalRecord.setHeightCm(request.getHeightCm());
        medicalRecord.setWeightKg(request.getWeightKg());
        medicalRecord.setNotes(request.getNotes());
    }

    private MedicalRecordResponse mapToResponse(MedicalRecord medicalRecord) {
        return MedicalRecordResponse.builder()
                .id(medicalRecord.getId())
                .bloodGroup(medicalRecord.getBloodGroup())
                .allergies(medicalRecord.getAllergies())
                .chronicConditions(medicalRecord.getChronicConditions())
                .currentMedications(medicalRecord.getCurrentMedications())
                .pastSurgeries(medicalRecord.getPastSurgeries())
                .familyMedicalHistory(medicalRecord.getFamilyMedicalHistory())
                .heightCm(medicalRecord.getHeightCm())
                .weightKg(medicalRecord.getWeightKg())
                .notes(medicalRecord.getNotes())
                .build();
    }
}