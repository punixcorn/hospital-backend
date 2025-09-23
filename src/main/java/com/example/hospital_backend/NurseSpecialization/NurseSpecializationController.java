package com.example.hospital_backend.NurseSpecialization;

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
@RequestMapping("/api/NurseSpecializations")
@CrossOrigin(origins = "*")
public class NurseSpecializationController {

    @Autowired
    private NurseSpecializationService NurseSpecializationService;

    // Get all NurseSpecializations
    @GetMapping
    public ResponseEntity<List<NurseSpecialization>> getAllNurseSpecializations() {
        List<NurseSpecialization> NurseSpecializations = NurseSpecializationService.getAllNurseSpecializations();
        return ResponseEntity.ok(NurseSpecializations);
    }

    // Get NurseSpecialization by ID
    @GetMapping("/{id}")
    public ResponseEntity<NurseSpecialization> getNurseSpecializationById(@PathVariable Long id) {
        Optional<NurseSpecialization> NurseSpecialization = NurseSpecializationService.getNurseSpecializationById(id);
        return NurseSpecialization.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new NurseSpecialization
    @PostMapping("/create")
    public ResponseEntity<NurseSpecialization> createNurseSpecialization(@RequestBody NurseSpecialization NurseSpecialization) {
        try {
            NurseSpecialization savedNurseSpecialization = NurseSpecializationService.saveNurseSpecialization(NurseSpecialization);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNurseSpecialization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing NurseSpecialization
    @PutMapping("/{id}")
    public ResponseEntity<NurseSpecialization> updateNurseSpecialization(@PathVariable Long id, @RequestBody NurseSpecialization NurseSpecialization) {
        Optional<NurseSpecialization> existingNurseSpecialization = NurseSpecializationService.getNurseSpecializationById(id);
        if (existingNurseSpecialization.isPresent()) {
            NurseSpecialization.setId(id); // Ensure the ID is set correctly
            NurseSpecialization updatedNurseSpecialization = NurseSpecializationService.saveNurseSpecialization(NurseSpecialization);
            return ResponseEntity.ok(updatedNurseSpecialization);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a NurseSpecialization
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurseSpecialization(@PathVariable Long id) {
        Optional<NurseSpecialization> existingNurseSpecialization = NurseSpecializationService.getNurseSpecializationById(id);
        if (existingNurseSpecialization.isPresent()) {
            NurseSpecializationService.deleteNurseSpecialization(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
