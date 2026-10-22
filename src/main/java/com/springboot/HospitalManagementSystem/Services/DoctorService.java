package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.DoctorRequest;
import com.springboot.HospitalManagementSystem.Dtos.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorRequest request);

    DoctorResponse getDoctorById(Long id);

    Page<DoctorResponse> getAllDoctors(Pageable pageable);

    Page<DoctorResponse> getDoctorsByDepartment(Long departmentId, Pageable pageable);

    Page<DoctorResponse> searchDoctorsByName(String name, Pageable pageable);

    Page<DoctorResponse> findAvailableDoctors(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<DoctorResponse> findAvailableDoctorsBySpecialty(
            Long specialtyId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    DoctorResponse updateDoctor(Long id, DoctorRequest request);

    void deleteDoctor(Long id);
}