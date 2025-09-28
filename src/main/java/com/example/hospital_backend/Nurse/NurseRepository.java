package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;

public interface NurseRepository extends JpaRepository<Nurse,Long> {
    List<Nurse> getNursesByAge(Integer age);
    List<Nurse> getNursesByAvailable(Boolean available);
    List<Nurse> getNursesByCertifications(List<Certification> certifications);
    List<Nurse> getNursesBySpecializations(List<Specialization> specializations);
    List<Nurse> getNursesByPreviousClients(PreviousClient previousClient);
    List<Nurse> getNursesByPreviousClientsId(List<Long> previousClientsId);
    List<Nurse> getNursesByAssignedClientsId(List<Long> assignedClientsId);
    List<Nurse> getNursesByAssignedClients(List<Client> assignedClients);
    List<Nurse> getNursesByEndDate(LocalDate endDate);
    List<Nurse> getNursesByStartDate(LocalDate startDate);
    
    // Additional methods for better functionality
    Optional<Nurse> findByUserId(Long userId);
    List<Nurse> findByAvailableTrue();
    List<Nurse> findByAvailableFalse();
    List<Nurse> findByExperienceGreaterThanEqual(Integer minExperience);
    List<Nurse> findByPayRateBetween(Double minPayRate, Double maxPayRate);
}
