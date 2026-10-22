package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Insurance;
import com.springboot.HospitalManagementSystem.Entities.Type.InsurancePlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Optional<Insurance> findByPolicyNumber(String policyNumber);

    boolean existsByPolicyNumber(String policyNumber);

    Page<Insurance> findByProviderNameContainingIgnoreCase(String providerName, Pageable pageable);

    Page<Insurance> findByPlanType(InsurancePlanType planType, Pageable pageable);

    Page<Insurance> findByActiveTrue(Pageable pageable);

    @Query("SELECT i FROM Insurance i WHERE i.active = true AND i.validUntil >= :date")
    Page<Insurance> findCurrentlyValidInsurances(@Param("date") LocalDate date, Pageable pageable);
}