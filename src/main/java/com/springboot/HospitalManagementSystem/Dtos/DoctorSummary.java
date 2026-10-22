package com.springboot.HospitalManagementSystem.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSummary {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}