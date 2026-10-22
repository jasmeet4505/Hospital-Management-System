package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.SpecialtyRequest;
import com.springboot.HospitalManagementSystem.Dtos.SpecialtyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialtyService {

    SpecialtyResponse createSpecialty(SpecialtyRequest request);

    SpecialtyResponse getSpecialtyById(Long id);

    Page<SpecialtyResponse> getAllSpecialties(Pageable pageable);

    Page<SpecialtyResponse> searchSpecialtiesByName(String name, Pageable pageable);

    SpecialtyResponse updateSpecialty(Long id, SpecialtyRequest request);

    void deleteSpecialty(Long id);
}
