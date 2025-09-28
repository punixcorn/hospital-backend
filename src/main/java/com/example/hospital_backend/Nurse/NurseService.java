package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Certification.CertificationRepository;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;
import com.example.hospital_backend.Specialization.SpecializationRepository;
import com.example.hospital_backend.User.User;
import com.example.hospital_backend.User.UserRepository;
import com.example.hospital_backend.User.UserRole;

@Service
@Transactional
public class NurseService {
    
    @Autowired
    private NurseRepository nurseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CertificationRepository certificationRepository;
    
    @Autowired
    private SpecializationRepository specializationRepository;
    
    /**
     * Get all nurses with their basic information
     */
    public List<NurseDTO> getAllNurses() {
        return nurseRepository.findAll().stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurse by ID with full details
     */
    public Optional<NurseDTO> getNurseById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return nurseRepository.findById(id)
            .flatMap(Nurse::toNurseDTO);
    }

    /**
     * Create a new nurse with validation
     */
    public NurseDTO createNurse(CreateNurseRequest request) {
        // Validate user exists and has NURSE role
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));
        
        if (user.getRole() != UserRole.NURSE) {
            throw new IllegalArgumentException("User must have NURSE role to create nurse profile");
        }
        
        // Check if nurse profile already exists for this user
        if (nurseRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Nurse profile already exists for user ID: " + request.getUserId());
        }
        
        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        
        // Build nurse entity
        Nurse nurse = Nurse.builder()
            .userId(request.getUserId())
            .image(request.getImage())
            .age(request.getAge())
            .height(request.getHeight())
            .experience(request.getExperience())
            .shiftStart(request.getShiftStart())
            .shiftEnd(request.getShiftEnd())
            .payRate(request.getPayRate())
            .available(request.getAvailable())
            .currentClient(request.getCurrentClient())
            .hoursWorked(request.getHoursWorked())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .totalHoursAssigned(request.getTotalHoursAssigned())
            .payPerHour(request.getPayPerHour())
            .build();
        
        // Add certifications if provided
        if (request.getCertificationIds() != null && !request.getCertificationIds().isEmpty()) {
            List<Certification> certifications = certificationRepository.findAllById(request.getCertificationIds());
            nurse.setCertifications(certifications);
        }
        
        // Add specializations if provided
        if (request.getSpecializationIds() != null && !request.getSpecializationIds().isEmpty()) {
            List<Specialization> specializations = specializationRepository.findAllById(request.getSpecializationIds());
            nurse.setSpecializations(specializations);
        }
        
        Nurse savedNurse = nurseRepository.save(nurse);
        return savedNurse.toNurseDTO().orElseThrow(() -> new RuntimeException("Failed to convert nurse to DTO"));
    }

    /**
     * Update an existing nurse
     */
    public NurseDTO updateNurse(Long id, UpdateNurseRequest request) {
        Nurse existingNurse = nurseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Nurse not found with ID: " + id));
        
        // Update fields if provided
        if (request.getImage() != null) {
            existingNurse.setImage(request.getImage());
        }
        if (request.getAge() != null) {
            existingNurse.setAge(request.getAge());
        }
        if (request.getHeight() != null) {
            existingNurse.setHeight(request.getHeight());
        }
        if (request.getExperience() != null) {
            existingNurse.setExperience(request.getExperience());
        }
        if (request.getShiftStart() != null) {
            existingNurse.setShiftStart(request.getShiftStart());
        }
        if (request.getShiftEnd() != null) {
            existingNurse.setShiftEnd(request.getShiftEnd());
        }
        if (request.getPayRate() != null) {
            existingNurse.setPayRate(request.getPayRate());
        }
        if (request.getAvailable() != null) {
            existingNurse.setAvailable(request.getAvailable());
        }
        if (request.getCurrentClient() != null) {
            existingNurse.setCurrentClient(request.getCurrentClient());
        }
        if (request.getHoursWorked() != null) {
            existingNurse.setHoursWorked(request.getHoursWorked());
        }
        if (request.getStartDate() != null) {
            existingNurse.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            existingNurse.setEndDate(request.getEndDate());
        }
        if (request.getTotalHoursAssigned() != null) {
            existingNurse.setTotalHoursAssigned(request.getTotalHoursAssigned());
        }
        if (request.getPayPerHour() != null) {
            existingNurse.setPayPerHour(request.getPayPerHour());
        }
        
        // Validate date range if both dates are being updated
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new IllegalArgumentException("End date must be after start date");
            }
        }
        
        // Update certifications if provided
        if (request.getCertificationIds() != null) {
            List<Certification> certifications = certificationRepository.findAllById(request.getCertificationIds());
            existingNurse.setCertifications(certifications);
        }
        
        // Update specializations if provided
        if (request.getSpecializationIds() != null) {
            List<Specialization> specializations = specializationRepository.findAllById(request.getSpecializationIds());
            existingNurse.setSpecializations(specializations);
        }
        
        Nurse updatedNurse = nurseRepository.save(existingNurse);
        return updatedNurse.toNurseDTO().orElseThrow(() -> new RuntimeException("Failed to convert nurse to DTO"));
    }

    /**
     * Delete a nurse
     */
    public void deleteNurse(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid nurse ID");
        }
        
        if (!nurseRepository.existsById(id)) {
            throw new IllegalArgumentException("Nurse not found with ID: " + id);
        }
        
        nurseRepository.deleteById(id);
    }

    
    /**
     * Get nurses by availability status
     */
    public List<NurseDTO> getNursesByAvailable(Boolean available) {
        List<Nurse> nurses = nurseRepository.getNursesByAvailable(available);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by previous clients
     */
    public List<NurseDTO> getNursesByPreviousClients(PreviousClient previousClient) {
        List<Nurse> nurses = nurseRepository.getNursesByPreviousClients(previousClient);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by certifications
     */
    public List<NurseDTO> getNursesByCertifications(List<Certification> certifications) {
        List<Nurse> nurses = nurseRepository.getNursesByCertifications(certifications);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by specializations
     */
    public List<NurseDTO> getNursesBySpecializations(List<Specialization> specializations) {
        List<Nurse> nurses = nurseRepository.getNursesBySpecializations(specializations);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by previous client IDs
     */
    public List<NurseDTO> getNursesByPreviousClientsId(List<Long> previousClientsIds) {
        List<Nurse> nurses = nurseRepository.getNursesByPreviousClientsId(previousClientsIds);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by assigned clients
     */
    public List<NurseDTO> getNursesByAssignedClients(List<Client> assignedClients) {
        List<Nurse> nurses = nurseRepository.getNursesByAssignedClients(assignedClients);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by assigned client IDs
     */
    public List<NurseDTO> getNursesByAssignedClientsId(List<Long> assignedClientsIds) {
        List<Nurse> nurses = nurseRepository.getNursesByAssignedClientsId(assignedClientsIds);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by end date
     */
    public List<NurseDTO> getNursesByEndDate(LocalDate endDate) {
        List<Nurse> nurses = nurseRepository.getNursesByEndDate(endDate);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by start date
     */
    public List<NurseDTO> getNursesByStartDate(LocalDate startDate) {
        List<Nurse> nurses = nurseRepository.getNursesByStartDate(startDate);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by minimum experience
     */
    public List<NurseDTO> getNursesByMinExperience(Integer minExperience) {
        List<Nurse> nurses = nurseRepository.findByExperienceGreaterThanEqual(minExperience);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get nurses by pay rate range
     */
    public List<NurseDTO> getNursesByPayRateRange(Double minPayRate, Double maxPayRate) {
        List<Nurse> nurses = nurseRepository.findByPayRateBetween(minPayRate, maxPayRate);
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get available nurses only
     */
    public List<NurseDTO> getAvailableNurses() {
        List<Nurse> nurses = nurseRepository.findByAvailableTrue();
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get unavailable nurses only
     */
    public List<NurseDTO> getUnavailableNurses() {
        List<Nurse> nurses = nurseRepository.findByAvailableFalse();
        return nurses.stream()
            .map(nurse -> nurse.toNurseDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }
}
