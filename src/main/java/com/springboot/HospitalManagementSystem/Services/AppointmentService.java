package com.springboot.HospitalManagementSystem.Services;

import com.springboot.HospitalManagementSystem.Dtos.AppointmentCancellationRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentResponse;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentResponse bookAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(Long id);

    Page<AppointmentResponse> getAllAppointments(Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable);

    Page<AppointmentResponse> getAppointmentsByStatus(AppointmentStatusType status, Pageable pageable);

    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);

    AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatusType status);

    AppointmentResponse cancelAppointment(Long id, AppointmentCancellationRequest request);

    void deleteAppointment(Long id);
}