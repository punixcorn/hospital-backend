package com.example.hospital_backend.NurseSpecialization;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseSpecializationService {

    @Autowired
    NurseSpecializationRepository NurseSpecializationRepository;

    public Optional<NurseSpecialization> getNurseSpecializationById(Long id) {
        return NurseSpecializationRepository.findById(id);
    }

    public List<NurseSpecialization> getAllNurseSpecializations() {
        return NurseSpecializationRepository.findAll();
    }

    public NurseSpecialization saveNurseSpecialization(NurseSpecialization NurseSpecialization) {
        return NurseSpecializationRepository.save(NurseSpecialization);
    }

    public void deleteNurseSpecialization(Long id) {
        NurseSpecializationRepository.deleteById(id);
    }

}
