package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.UpdateUserRequest;
import com.springboot.HospitalManagementSystem.Dtos.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void enableUser(Long id);

    void disableUser(Long id);

    void lockUser(Long id);

    void unlockUser(Long id);

    void deleteUser(Long id);
}