package com.springboot.HospitalManagementSystem.Services.Implementations;

import com.springboot.HospitalManagementSystem.Dtos.SpecialtyRequest;
import com.springboot.HospitalManagementSystem.Dtos.SpecialtyResponse;
import com.springboot.HospitalManagementSystem.Entities.Specialty;
import com.springboot.HospitalManagementSystem.Exceptions.DuplicateResourceException;
import com.springboot.HospitalManagementSystem.Exceptions.ResourceNotFoundException;
import com.springboot.HospitalManagementSystem.Repositories.SpecialtyRepository;
import com.springboot.HospitalManagementSystem.Services.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    @Transactional
    public SpecialtyResponse createSpecialty(SpecialtyRequest request) {
        if (specialtyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Specialty already exists with name: " + request.getName());
        }
        Specialty specialty = new Specialty();
        specialty.setName(request.getName());
        specialty.setDescription(request.getDescription());
        Specialty saved = specialtyRepository.save(specialty);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyResponse getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", id));
        return mapToResponse(specialty);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> getAllSpecialties(Pageable pageable) {
        return specialtyRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> searchSpecialtiesByName(String name, Pageable pageable) {
        return specialtyRepository.findByNameContainingIgnoreCase(name, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public SpecialtyResponse updateSpecialty(Long id, SpecialtyRequest request) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", id));
        if (!specialty.getName().equalsIgnoreCase(request.getName())
                && specialtyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Specialty already exists with name: " + request.getName());
        }
        specialty.setName(request.getName());
        specialty.setDescription(request.getDescription());
        Specialty updated = specialtyRepository.save(specialty);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteSpecialty(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty", id));
        specialtyRepository.delete(specialty);
    }

    private SpecialtyResponse mapToResponse(Specialty specialty) {
        return SpecialtyResponse.builder()
                .id(specialty.getId())
                .name(specialty.getName())
                .description(specialty.getDescription())
                .build();
    }
}