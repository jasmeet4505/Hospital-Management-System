package com.springboot.HospitalManagementSystem.Dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "Patient id is required")
    private Long patientId;

    @NotNull(message = "Doctor id is required")
    private Long doctorId;

    @NotNull(message = "Scheduled start time is required")
    @Future(message = "Scheduled start time must be in the future")
    private LocalDateTime scheduledStartTime;

    @NotNull(message = "Scheduled end time is required")
    @Future(message = "Scheduled end time must be in the future")
    private LocalDateTime scheduledEndTime;

    @Size(max = 500, message = "Reason for visit must not exceed 500 characters")
    private String reasonForVisit;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
}