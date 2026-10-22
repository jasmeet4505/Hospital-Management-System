package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.MedicalRecord;
import com.springboot.HospitalManagementSystem.Entities.Type.BloodGroupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    Page<MedicalRecord> findByBloodGroup(BloodGroupType bloodGroup, Pageable pageable);

    Page<MedicalRecord> findByAllergiesContainingIgnoreCase(String allergyKeyword, Pageable pageable);

    Page<MedicalRecord> findByChronicConditionsContainingIgnoreCase(String conditionKeyword, Pageable pageable);
}