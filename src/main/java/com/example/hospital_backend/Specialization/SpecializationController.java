package com.example.hospital_backend.Specialization;

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
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "*")
public class SpecializationController {

    @Autowired
    private SpecializationService SpecializationService;

    // Get all Specializations
    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        List<Specialization> Specializations = SpecializationService.getAllSpecializations();
        return ResponseEntity.ok(Specializations);
    }

    // Get Specialization by ID
    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable Long id) {
        Optional<Specialization> Specialization = SpecializationService.getSpecializationById(id);
        return Specialization.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Specialization
    @PostMapping("/create")
    public ResponseEntity<Specialization> createSpecialization(@RequestBody Specialization Specialization) {
        try {
            Specialization savedSpecialization = SpecializationService.saveSpecialization(Specialization);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSpecialization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Specialization
    @PutMapping("/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable Long id, @RequestBody Specialization Specialization) {
        Optional<Specialization> existingSpecialization = SpecializationService.getSpecializationById(id);
        if (existingSpecialization.isPresent()) {
            Specialization.setId(id); // Ensure the ID is set correctly
            Specialization updatedSpecialization = SpecializationService.saveSpecialization(Specialization);
            return ResponseEntity.ok(updatedSpecialization);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Specialization
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        Optional<Specialization> existingSpecialization = SpecializationService.getSpecializationById(id);
        if (existingSpecialization.isPresent()) {
            SpecializationService.deleteSpecialization(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
