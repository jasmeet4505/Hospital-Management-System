package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Department;
import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    boolean existsByName(String name);

    Page<Department> findByStatus(RecordStatusType status, Pageable pageable);

    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Department> findByFloorNumber(Integer floorNumber);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.doctors WHERE d.id = :id")
    Optional<Department> findByIdWithDoctors(@Param("id") Long id);

    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.doctors WHERE d.status = :status")
    List<Department> findAllWithDoctorsByStatus(@Param("status") RecordStatusType status);
}