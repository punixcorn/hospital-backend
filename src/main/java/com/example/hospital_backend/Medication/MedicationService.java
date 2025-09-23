package com.example.hospital_backend.Medication;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {

    @Autowired
    MedicationRepository MedicationRepository;

    public Optional<Medication> getMedicationById(Long id) {
        return MedicationRepository.findById(id);
    }

    public List<Medication> getAllMedications() {
        return MedicationRepository.findAll();
    }

    public Medication saveMedication(Medication Medication) {
        return MedicationRepository.save(Medication);
    }

    public void deleteMedication(Long id) {
        MedicationRepository.deleteById(id);
    }

}
