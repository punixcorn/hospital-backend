package com.example.hospital_backend.Medication;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medications")
@CrossOrigin(origins = "*")
public class MedicationController {

    @Autowired
    private MedicationService MedicationService;

    // Get all Medications
    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> Medications = MedicationService.getAllMedications();
        return ResponseEntity.ok(Medications);
    }

    // Get Medication by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        Optional<Medication> Medication = MedicationService.getMedicationById(id);
        return Medication.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Medication
    @PostMapping("/create")
    public ResponseEntity<Medication> createMedication(@RequestBody Medication Medication) {
        try {
            Medication savedMedication = MedicationService.saveMedication(Medication);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Medication
    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable Long id, @RequestBody Medication Medication) {
        Optional<Medication> existingMedication = MedicationService.getMedicationById(id);
        if (existingMedication.isPresent()) {
            Medication.setId(id); // Ensure the ID is set correctly
            Medication updatedMedication = MedicationService.saveMedication(Medication);
            return ResponseEntity.ok(updatedMedication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Medication
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        Optional<Medication> existingMedication = MedicationService.getMedicationById(id);
        if (existingMedication.isPresent()) {
            MedicationService.deleteMedication(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
