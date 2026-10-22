package com.springboot.HospitalManagementSystem.Repositories;

import com.springboot.HospitalManagementSystem.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.privileges WHERE r.name = :name")
    Optional<Role> findByNameWithPrivileges(@Param("name") String name);
}