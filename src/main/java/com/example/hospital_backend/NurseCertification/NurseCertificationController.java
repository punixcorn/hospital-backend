package com.example.hospital_backend.NurseCertification;


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
@RequestMapping("/api/NurseCertifications")
@CrossOrigin(origins = "*")
public class NurseCertificationController {

    @Autowired
    private NurseCertificationService NurseCertificationService;

    // Get all NurseCertifications
    @GetMapping
    public ResponseEntity<List<NurseCertification>> getAllNurseCertifications() {
        List<NurseCertification> NurseCertifications = NurseCertificationService.getAllNurseCertifications();
        return ResponseEntity.ok(NurseCertifications);
    }

    // Get NurseCertification by ID
    @GetMapping("/{id}")
    public ResponseEntity<NurseCertification> getNurseCertificationById(@PathVariable Long id) {
        Optional<NurseCertification> NurseCertification = NurseCertificationService.getNurseCertificationById(id);
        return NurseCertification.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new NurseCertification
    @PostMapping("/create")
    public ResponseEntity<NurseCertification> createNurseCertification(@RequestBody NurseCertification NurseCertification) {
        try {
            NurseCertification savedNurseCertification = NurseCertificationService.saveNurseCertification(NurseCertification);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNurseCertification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing NurseCertification
    @PutMapping("/{id}")
    public ResponseEntity<NurseCertification> updateNurseCertification(@PathVariable Long id, @RequestBody NurseCertification NurseCertification) {
        Optional<NurseCertification> existingNurseCertification = NurseCertificationService.getNurseCertificationById(id);
        if (existingNurseCertification.isPresent()) {
            NurseCertification.setId(id); // Ensure the ID is set correctly
            NurseCertification updatedNurseCertification = NurseCertificationService.saveNurseCertification(NurseCertification);
            return ResponseEntity.ok(updatedNurseCertification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a NurseCertification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurseCertification(@PathVariable Long id) {
        Optional<NurseCertification> existingNurseCertification = NurseCertificationService.getNurseCertificationById(id);
        if (existingNurseCertification.isPresent()) {
            NurseCertificationService.deleteNurseCertification(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
