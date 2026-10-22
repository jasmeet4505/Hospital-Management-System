package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Doctor;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import com.springboot.HospitalManagementSystem.Entities.Type.DoctorAvailabilityStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    Page<Doctor> findByDepartmentId(Long departmentId, Pageable pageable);

    Page<Doctor> findByAvailabilityStatus(DoctorAvailabilityStatusType availabilityStatus, Pageable pageable);

    Page<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.department LEFT JOIN FETCH d.specialties WHERE d.id = :id")
    Optional<Doctor> findByIdWithDepartmentAndSpecialties(@Param("id") Long id);

    @Query("SELECT DISTINCT d FROM Doctor d JOIN d.specialties s WHERE s.id = :specialtyId")
    Page<Doctor> findBySpecialtyId(@Param("specialtyId") Long specialtyId, Pageable pageable);

    @Query("""
            SELECT DISTINCT d FROM Doctor d
            LEFT JOIN FETCH d.department dept
            WHERE d.availabilityStatus = :availabilityStatus
            AND NOT EXISTS (
                SELECT 1 FROM Appointment a
                WHERE a.doctor = d
                AND a.status NOT IN :excludedStatuses
                AND a.scheduledStartTime < :endTime
                AND a.scheduledEndTime > :startTime
            )
            """)
    Page<Doctor> findAvailableDoctors(
            @Param("availabilityStatus") DoctorAvailabilityStatusType availabilityStatus,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludedStatuses") List<AppointmentStatusType> excludedStatuses,
            Pageable pageable);

    @Query("""
            SELECT DISTINCT d FROM Doctor d
            LEFT JOIN FETCH d.department dept
            JOIN d.specialties s
            WHERE s.id = :specialtyId
            AND d.availabilityStatus = :availabilityStatus
            AND NOT EXISTS (
                SELECT 1 FROM Appointment a
                WHERE a.doctor = d
                AND a.status NOT IN :excludedStatuses
                AND a.scheduledStartTime < :endTime
                AND a.scheduledEndTime > :startTime
            )
            """)
    Page<Doctor> findAvailableDoctorsBySpecialty(
            @Param("specialtyId") Long specialtyId,
            @Param("availabilityStatus") DoctorAvailabilityStatusType availabilityStatus,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludedStatuses") List<AppointmentStatusType> excludedStatuses,
            Pageable pageable);
}