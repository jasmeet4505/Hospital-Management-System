package com.springboot.HospitalManagementSystem.Dtos;

import com.springboot.HospitalManagementSystem.Entities.Type.RecordStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private Integer floorNumber;

    @Size(max = 20, message = "Contact number must not exceed 20 characters")
    private String contactNumber;

    @NotNull(message = "Status is required")
    private RecordStatusType status;
}