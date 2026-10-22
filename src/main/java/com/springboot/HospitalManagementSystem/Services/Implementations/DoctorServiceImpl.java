package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.DepartmentResponse;
import com.springboot.HospitalManagementSystem.Dtos.DoctorRequest;
import com.springboot.HospitalManagementSystem.Dtos.DoctorResponse;
import com.springboot.HospitalManagementSystem.Dtos.SpecialtyResponse;
import com.springboot.HospitalManagementSystem.Entities.Department;
import com.springboot.HospitalManagementSystem.Entities.Doctor;
import com.springboot.HospitalManagementSystem.Entities.Specialty;
import com.springboot.HospitalManagementSystem.Entities.Type.AppointmentStatusType;
import com.springboot.HospitalManagementSystem.Entities.Type.DoctorAvailabilityStatusType;
import com.springboot.HospitalManagementSystem.Exceptions.DuplicateResourceException;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.DepartmentRepository;
import com.springboot.HospitalManagementSystem.Repositories.DoctorRepository;
import com.springboot.HospitalManagementSystem.Repositories.SpecialtyRepository;
import com.springboot.HospitalManagementSystem.Services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final SpecialtyRepository specialtyRepository;

    private static final List<AppointmentStatusType> BLOCKING_STATUSES = List.of(
            AppointmentStatusType.CANCELLED, AppointmentStatusType.NO_SHOW);

    @Override
    @Transactional
    public DoctorResponse createDoctor(DoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Doctor already exists with email: " + request.getEmail());
        }
        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException(
                    "Doctor already exists with license number: " + request.getLicenseNumber());
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));
        Set<Specialty> specialties = resolveSpecialties(request.getSpecialtyIds());

        Doctor doctor = new Doctor();
        applyRequest(doctor, request, department, specialties);
        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findByIdWithDepartmentAndSpecialties(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
        return mapToResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> getDoctorsByDepartment(Long departmentId, Pageable pageable) {
        return doctorRepository.findByDepartmentId(departmentId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> searchDoctorsByName(String name, Pageable pageable) {
        return doctorRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> findAvailableDoctors(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return doctorRepository
                .findAvailableDoctors(DoctorAvailabilityStatusType.AVAILABLE, startTime, endTime, BLOCKING_STATUSES, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> findAvailableDoctorsBySpecialty(
            Long specialtyId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return doctorRepository
                .findAvailableDoctorsBySpecialty(
                        specialtyId, DoctorAvailabilityStatusType.AVAILABLE, startTime, endTime, BLOCKING_STATUSES, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
        if (!doctor.getEmail().equals(request.getEmail()) && doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Doctor already exists with email: " + request.getEmail());
        }
        if (!doctor.getLicenseNumber().equals(request.getLicenseNumber())
                && doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException(
                    "Doctor already exists with license number: " + request.getLicenseNumber());
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.getDepartmentId()));
        Set<Specialty> specialties = resolveSpecialties(request.getSpecialtyIds());

        applyRequest(doctor, request, department, specialties);
        Doctor updated = doctorRepository.save(doctor);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
        doctorRepository.delete(doctor);
    }

    private Set<Specialty> resolveSpecialties(Set<Long> specialtyIds) {
        List<Specialty> found = specialtyRepository.findAllByIdIn(specialtyIds.stream().toList());
        if (found.size() != specialtyIds.size()) {
            throw new ResourceNotFoundException("One or more specialties could not be found");
        }
        return found.stream().collect(Collectors.toSet());
    }

    private void applyRequest(Doctor doctor, DoctorRequest request, Department department, Set<Specialty> specialties) {
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setGender(request.getGender());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setAvailabilityStatus(request.getAvailabilityStatus());
        doctor.setDepartment(department);
        doctor.setSpecialties(specialties);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        DepartmentResponse departmentResponse = null;
        if (doctor.getDepartment() != null) {
            departmentResponse = DepartmentResponse.builder()
                    .id(doctor.getDepartment().getId())
                    .name(doctor.getDepartment().getName())
                    .description(doctor.getDepartment().getDescription())
                    .floorNumber(doctor.getDepartment().getFloorNumber())
                    .contactNumber(doctor.getDepartment().getContactNumber())
                    .status(doctor.getDepartment().getStatus())
                    .build();
        }
        Set<SpecialtyResponse> specialtyResponses = doctor.getSpecialties() == null
                ? Set.of()
                : doctor.getSpecialties().stream()
                .map(s -> SpecialtyResponse.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .description(s.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return DoctorResponse.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .gender(doctor.getGender())
                .dateOfBirth(doctor.getDateOfBirth())
                .licenseNumber(doctor.getLicenseNumber())
                .yearsOfExperience(doctor.getYearsOfExperience())
                .consultationFee(doctor.getConsultationFee())
                .availabilityStatus(doctor.getAvailabilityStatus())
                .department(departmentResponse)
                .specialties(specialtyResponses)
                .build();
    }
}