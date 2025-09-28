package com.example.hospital_backend.Certification;

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
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "*")
public class CertificationController {

    @Autowired
    private CertificationService CertificationService;

    // Get all Certifications
    @GetMapping
    public ResponseEntity<List<Certification>> getAllCertifications() {
        List<Certification> Certifications = CertificationService.getAllCertifications();
        return ResponseEntity.ok(Certifications);
    }

    // Get Certification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Certification> getCertificationById(@PathVariable Long id) {
        Optional<Certification> Certification = CertificationService.getCertificationById(id);
        return Certification.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Certification
    @PostMapping("/create")
    public ResponseEntity<Certification> createCertification(@RequestBody Certification Certification) {
        try {
            Certification savedCertification = CertificationService.saveCertification(Certification);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCertification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Certification
    @PutMapping("/{id}")
    public ResponseEntity<Certification> updateCertification(@PathVariable Long id, @RequestBody Certification Certification) {
        Optional<Certification> existingCertification = CertificationService.getCertificationById(id);
        if (existingCertification.isPresent()) {
            Certification.setId(id); // Ensure the ID is set correctly
            Certification updatedCertification = CertificationService.saveCertification(Certification);
            return ResponseEntity.ok(updatedCertification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Certification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        Optional<Certification> existingCertification = CertificationService.getCertificationById(id);
        if (existingCertification.isPresent()) {
            CertificationService.deleteCertification(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
