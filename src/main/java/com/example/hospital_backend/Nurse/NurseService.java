package com.example.hospital_backend.Nurse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;

@Service
public class NurseService {
    @Autowired
    private NurseRepository nurseRepository;
    
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Optional<NurseDTO> getNurseById(Long id) {
         if(nurseRepository.findById(id).isEmpty())
            return Optional.empty();
        return nurseRepository.findById(id).get().toNurseDTO();
    }

    public Nurse saveNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }

    
    public List<Nurse> getNursesByAvailable(Boolean available) {
        return nurseRepository.getNursesByAvailable( available);
    }

    public List<Nurse> getNursesByPreviousClients(PreviousClient previousClient){
        return nurseRepository.getNursesByPreviousClients(previousClient);
    }

    public List<Nurse> getNursesByCertifications(List<Certification> certifications) {
        return nurseRepository.getNursesByCertifications(certifications);
    }

    public List<Nurse> getNursesBySpecializations(List<Specialization> specializations) {
        return nurseRepository.getNursesBySpecializations(specializations);
    }

    public List<Nurse> getNursesByPreviousClientsId(List<Long> previousClientsIds) {
        return nurseRepository.getNursesByPreviousClientsId(previousClientsIds);
    }

    public List<Nurse> getNursesByAssignedClients(List<Client> assignedClients) {
        return nurseRepository.getNursesByAssignedClients(assignedClients);
    }

    public List<Nurse> getNursesByAssignedClientsId(List<Long> assignedClientsIds) {
        return nurseRepository.getNursesByAssignedClientsId(assignedClientsIds);
    }

    public List<Nurse> getNursesByEndDate(LocalDate endDate) {
        return nurseRepository.getNursesByEndDate(endDate);
    }

    public List<Nurse> getNursesByStartDate(LocalDate startDate) {
        return nurseRepository.getNursesByStartDate(startDate);
    }
}
