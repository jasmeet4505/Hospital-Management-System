package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Patient;
import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Patient> findByStatus(RecordStatusType status, Pageable pageable);

    Page<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    Page<Patient> findByInsuranceId(Long insuranceId, Pageable pageable);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.medicalRecord LEFT JOIN FETCH p.insurance WHERE p.id = :id")
    Optional<Patient> findByIdWithMedicalRecordAndInsurance(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.insurance WHERE p.status = :status")
    Page<Patient> findAllWithInsuranceByStatus(@Param("status") RecordStatusType status, Pageable pageable);
}