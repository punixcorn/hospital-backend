package com.example.hospital_backend.Specialization;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecializationService {

    @Autowired
    SpecializationRepository SpecializationRepository;

    public Optional<Specialization> getSpecializationById(Long id) {
        return SpecializationRepository.findById(id);
    }

    public List<Specialization> getAllSpecializations() {
        return SpecializationRepository.findAll();
    }

    public Specialization saveSpecialization(Specialization Specialization) {
        return SpecializationRepository.save(Specialization);
    }

    public void deleteSpecialization(Long id) {
        SpecializationRepository.deleteById(id);
    }

}
