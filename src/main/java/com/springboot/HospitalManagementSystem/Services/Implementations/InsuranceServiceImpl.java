package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.InsuranceRequest;
import com.springboot.HospitalManagementSystem.Dtos.InsuranceResponse;
import com.springboot.HospitalManagementSystem.Entities.Insurance;
import com.springboot.HospitalManagementSystem.Exceptions.DuplicateResourceException;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.InsuranceRepository;
import com.springboot.HospitalManagementSystem.Services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Override
    @Transactional
    public InsuranceResponse createInsurance(InsuranceRequest request) {
        if (insuranceRepository.existsByPolicyNumber(request.getPolicyNumber())) {
            throw new DuplicateResourceException(
                    "Insurance already exists with policy number: " + request.getPolicyNumber());
        }
        Insurance insurance = new Insurance();
        applyRequest(insurance, request);
        Insurance saved = insuranceRepository.save(insurance);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InsuranceResponse getInsuranceById(Long id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance", id));
        return mapToResponse(insurance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceResponse> getAllInsurances(Pageable pageable) {
        return insuranceRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceResponse> getActiveInsurances(Pageable pageable) {
        return insuranceRepository.findByActiveTrue(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public InsuranceResponse updateInsurance(Long id, InsuranceRequest request) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance", id));
        if (!insurance.getPolicyNumber().equals(request.getPolicyNumber())
                && insuranceRepository.existsByPolicyNumber(request.getPolicyNumber())) {
            throw new DuplicateResourceException(
                    "Insurance already exists with policy number: " + request.getPolicyNumber());
        }
        applyRequest(insurance, request);
        Insurance updated = insuranceRepository.save(insurance);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteInsurance(Long id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance", id));
        insuranceRepository.delete(insurance);
    }

    private void applyRequest(Insurance insurance, InsuranceRequest request) {
        insurance.setProviderName(request.getProviderName());
        insurance.setPolicyNumber(request.getPolicyNumber());
        insurance.setPlanType(request.getPlanType());
        insurance.setCoverageAmount(request.getCoverageAmount());
        insurance.setPremiumAmount(request.getPremiumAmount());
        insurance.setValidFrom(request.getValidFrom());
        insurance.setValidUntil(request.getValidUntil());
        insurance.setActive(request.getActive());
    }

    private InsuranceResponse mapToResponse(Insurance insurance) {
        return InsuranceResponse.builder()
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
}