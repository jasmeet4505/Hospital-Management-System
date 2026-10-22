package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.AppointmentCancellationRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentRequest;
import com.springboot.HospitalManagementSystem.Dtos.AppointmentResponse;
import com.springboot.HospitalManagementSystem.Dtos.DoctorSummary;
import com.springboot.HospitalManagementSystem.Dtos.PatientSummary;
import com.springboot.HospitalManagementSystem.Entities.Appointment;
import com.springboot.HospitalManagementSystem.Entities.Doctor;
import com.springboot.HospitalManagementSystem.Entities.Patient;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.AppointmentRepository;
import com.springboot.HospitalManagementSystem.Repositories.DoctorRepository;
import com.springboot.HospitalManagementSystem.Repositories.PatientRepository;
import com.springboot.HospitalManagementSystem.Services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private static final List<AppointmentStatusType> BLOCKING_STATUSES = List.of(
            AppointmentStatusType.CANCELLED, AppointmentStatusType.NO_SHOW);

    @Override
    @Transactional
    public AppointmentResponse bookAppointment(AppointmentRequest request) {
        if (!request.getScheduledEndTime().isAfter(request.getScheduledStartTime())) {
            throw new IllegalArgumentException("Scheduled end time must be after scheduled start time");
        }
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", request.getDoctorId()));

        try {
            boolean overlapping = !appointmentRepository.findOverlappingAppointmentsWithLock(
                    doctor.getId(), request.getScheduledStartTime(), request.getScheduledEndTime(), BLOCKING_STATUSES).isEmpty();
            if (overlapping) {
                throw new IllegalStateException(
                        "Doctor with id " + doctor.getId() + " is not available in the requested time slot");
            }
        } catch (PessimisticLockingFailureException ex) {
            throw new IllegalStateException(
                    "Appointment slot is currently being booked by another request. Please try again.");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setScheduledStartTime(request.getScheduledStartTime());
        appointment.setScheduledEndTime(request.getScheduledEndTime());
        appointment.setReasonForVisit(request.getReasonForVisit());
        appointment.setNotes(request.getNotes());
        appointment.setConsultationFeeCharged(doctor.getConsultationFee());
        appointment.setStatus(AppointmentStatusType.REQUESTED);

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findByIdWithPatientAndDoctor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        return mapToResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAppointmentsByPatient(Long patientId, Pageable pageable) {
        return appointmentRepository.findByPatientId(patientId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, Pageable pageable) {
        return appointmentRepository.findByDoctorId(doctorId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> getAppointmentsByStatus(AppointmentStatusType status, Pageable pageable) {
        return appointmentRepository.findAllWithPatientAndDoctorByStatus(status, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        if (!request.getScheduledEndTime().isAfter(request.getScheduledStartTime())) {
            throw new IllegalArgumentException("Scheduled end time must be after scheduled start time");
        }
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", request.getDoctorId()));

        boolean timeOrDoctorChanged = !appointment.getDoctor().getId().equals(doctor.getId())
                || !appointment.getScheduledStartTime().equals(request.getScheduledStartTime())
                || !appointment.getScheduledEndTime().equals(request.getScheduledEndTime());

        if (timeOrDoctorChanged) {
            try {
                boolean overlapping = !appointmentRepository.findOverlappingAppointmentsWithLock(
                        doctor.getId(), request.getScheduledStartTime(), request.getScheduledEndTime(), BLOCKING_STATUSES).isEmpty();
                if (overlapping) {
                    throw new IllegalStateException(
                            "Doctor with id " + doctor.getId() + " is not available in the requested time slot");
                }
            } catch (PessimisticLockingFailureException ex) {
                throw new IllegalStateException(
                        "Appointment slot is currently being booked by another request. Please try again.");
            }
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setScheduledStartTime(request.getScheduledStartTime());
        appointment.setScheduledEndTime(request.getScheduledEndTime());
        appointment.setReasonForVisit(request.getReasonForVisit());
        appointment.setNotes(request.getNotes());

        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatusType status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointment.setStatus(status);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(Long id, AppointmentCancellationRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointment.setStatus(AppointmentStatusType.CANCELLED);
        appointment.setCancellationReason(request.getCancellationReason());
        appointment.setCancelledBy(request.getCancelledBy());
        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointmentRepository.delete(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        PatientSummary patientSummary = null;
        if (appointment.getPatient() != null) {
            patientSummary = PatientSummary.builder()
                    .id(appointment.getPatient().getId())
                    .firstName(appointment.getPatient().getFirstName())
                    .lastName(appointment.getPatient().getLastName())
                    .email(appointment.getPatient().getEmail())
                    .build();
        }
        DoctorSummary doctorSummary = null;
        if (appointment.getDoctor() != null) {
            doctorSummary = DoctorSummary.builder()
                    .id(appointment.getDoctor().getId())
                    .firstName(appointment.getDoctor().getFirstName())
                    .lastName(appointment.getDoctor().getLastName())
                    .email(appointment.getDoctor().getEmail())
                    .build();
        }

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patient(patientSummary)
                .doctor(doctorSummary)
                .scheduledStartTime(appointment.getScheduledStartTime())
                .scheduledEndTime(appointment.getScheduledEndTime())
                .actualStartTime(appointment.getActualStartTime())
                .actualEndTime(appointment.getActualEndTime())
                .status(appointment.getStatus())
                .reasonForVisit(appointment.getReasonForVisit())
                .consultationFeeCharged(appointment.getConsultationFeeCharged())
                .cancellationReason(appointment.getCancellationReason())
                .cancelledBy(appointment.getCancelledBy())
                .notes(appointment.getNotes())
                .createdAt(appointment.getCreatedAt())
                .build();
    }
}