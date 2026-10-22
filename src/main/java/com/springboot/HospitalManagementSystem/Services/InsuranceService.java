package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.InsuranceRequest;
import com.springboot.HospitalManagementSystem.Dtos.InsuranceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InsuranceService {

    InsuranceResponse createInsurance(InsuranceRequest request);

    InsuranceResponse getInsuranceById(Long id);

    Page<InsuranceResponse> getAllInsurances(Pageable pageable);

    Page<InsuranceResponse> getActiveInsurances(Pageable pageable);

    InsuranceResponse updateInsurance(Long id, InsuranceRequest request);

    void deleteInsurance(Long id);
}