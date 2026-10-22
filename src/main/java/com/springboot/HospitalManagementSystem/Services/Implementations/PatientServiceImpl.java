package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.InsuranceResponse;
import com.springboot.HospitalManagementSystem.Dtos.MedicalRecordResponse;
import com.springboot.HospitalManagementSystem.Dtos.PatientRequest;
import com.springboot.HospitalManagementSystem.Dtos.PatientResponse;
import com.springboot.HospitalManagementSystem.Entities.Insurance;
import com.springboot.HospitalManagementSystem.Entities.MedicalRecord;
import com.springboot.HospitalManagementSystem.Entities.Patient;
import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import com.springboot.HospitalManagementSystem.Exceptions.DuplicateResourceException;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.InsuranceRepository;
import com.springboot.HospitalManagementSystem.Repositories.PatientRepository;
import com.springboot.HospitalManagementSystem.Services.MedicalRecordService;
import com.springboot.HospitalManagementSystem.Services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final MedicalRecordService medicalRecordService;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Patient already exists with email: " + request.getEmail());
        }
        Insurance insurance = resolveInsurance(request.getInsuranceId());

        Patient patient = new Patient();
        applyRequest(patient, request, insurance);
        if (request.getMedicalRecord() != null) {
            patient.setMedicalRecord(medicalRecordService.buildFromRequest(request.getMedicalRecord()));
        }
        Patient saved = patientRepository.save(patient);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findByIdWithMedicalRecordAndInsurance(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        return mapToResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getPatientsByStatus(RecordStatusType status, Pageable pageable) {
        return patientRepository.findByStatus(status, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> searchPatientsByName(String name, Pageable pageable) {
        return patientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        if (!patient.getEmail().equals(request.getEmail()) && patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Patient already exists with email: " + request.getEmail());
        }
        Insurance insurance = resolveInsurance(request.getInsuranceId());

        applyRequest(patient, request, insurance);
        if (request.getMedicalRecord() != null) {
            if (patient.getMedicalRecord() != null) {
                medicalRecordService.applyRequest(patient.getMedicalRecord(), request.getMedicalRecord());
            } else {
                patient.setMedicalRecord(medicalRecordService.buildFromRequest(request.getMedicalRecord()));
            }
        }
        Patient updated = patientRepository.save(patient);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        patientRepository.delete(patient);
    }

    @Override
    @Transactional
    public void softDeletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        patient.setStatus(RecordStatusType.DELETED);
        patientRepository.save(patient);
    }

    private Insurance resolveInsurance(Long insuranceId) {
        if (insuranceId == null) {
            return null;
        }
        return insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance", insuranceId));
    }

    private void applyRequest(Patient patient, PatientRequest request, Insurance insurance) {
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactNumber(request.getEmergencyContactNumber());
        patient.setStatus(request.getStatus());
        patient.setInsurance(insurance);
    }

    private PatientResponse mapToResponse(Patient patient) {
        MedicalRecordResponse medicalRecordResponse = null;
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        if (medicalRecord != null) {
            medicalRecordResponse = MedicalRecordResponse.builder()
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

        InsuranceResponse insuranceResponse = null;
        Insurance insurance = patient.getInsurance();
        if (insurance != null) {
            insuranceResponse = InsuranceResponse.builder()
                    .id(insurance.getId())
                    .providerName(insurance.getProviderName())
                    .policyNumber(insurance.getPolicyNumber())
                    .planType(insurance.getPlanType())
                    .coverageAmount(insurance.getCoverageAmount())
                    .premiumAmount(insurance.getPremiumAmount())
                    .validFrom(insurance.getValidFrom())
                    .validUntil(insurance.getValidUntil())
                    .active(insurance.getActive())
                    .build();
        }

        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .address(patient.getAddress())
                .emergencyContactName(patient.getEmergencyContactName())
                .emergencyContactNumber(patient.getEmergencyContactNumber())
                .status(patient.getStatus())
                .medicalRecord(medicalRecordResponse)
                .insurance(insuranceResponse)
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }
}