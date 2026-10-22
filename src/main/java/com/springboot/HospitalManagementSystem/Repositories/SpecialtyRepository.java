package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    Optional<Specialty> findByName(String name);

    boolean existsByName(String name);

    Page<Specialty> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT s FROM Specialty s LEFT JOIN FETCH s.doctors WHERE s.id = :id")
    Optional<Specialty> findByIdWithDoctors(@Param("id") Long id);

    @Query("SELECT s FROM Specialty s WHERE s.id IN :ids")
    List<Specialty> findAllByIdIn(@Param("ids") List<Long> ids);
}