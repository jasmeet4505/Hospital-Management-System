package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Appointment;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByStatus(AppointmentStatusType status, Pageable pageable);

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    Page<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatusType status, Pageable pageable);

    Page<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatusType status, Pageable pageable);

    Page<Appointment> findByScheduledStartTimeBetween(
            LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("""
            SELECT a FROM Appointment a
            JOIN FETCH a.patient p
            JOIN FETCH a.doctor d
            WHERE a.id = :id
            """)
    Optional<Appointment> findByIdWithPatientAndDoctor(@Param("id") Long id);

    @Query("""
            SELECT a FROM Appointment a
            JOIN FETCH a.patient p
            JOIN FETCH a.doctor d
            WHERE a.status = :status
            """)
    Page<Appointment> findAllWithPatientAndDoctorByStatus(
            @Param("status") AppointmentStatusType status, Pageable pageable);

    @Query("""
            SELECT a FROM Appointment a
            JOIN FETCH a.patient p
            JOIN FETCH a.doctor d
            WHERE d.id = :doctorId
            AND a.scheduledStartTime >= :startOfDay
            AND a.scheduledStartTime < :endOfDay
            AND a.status NOT IN :excludedStatuses
            ORDER BY a.scheduledStartTime ASC
            """)
    List<Appointment> findDoctorScheduleForDay(
            @Param("doctorId") Long doctorId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("excludedStatuses") List<AppointmentStatusType> excludedStatuses);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT a FROM Appointment a
            WHERE a.doctor.id = :doctorId
            AND a.status NOT IN :excludedStatuses
            AND a.scheduledStartTime < :endTime
            AND a.scheduledEndTime > :startTime
            """)
    List<Appointment> findOverlappingAppointmentsWithLock(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludedStatuses") List<AppointmentStatusType> excludedStatuses);
}