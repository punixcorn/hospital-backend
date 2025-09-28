package com.example.hospital_backend.AssignedNurse;

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
@RequestMapping("/api/assignednurses")
@CrossOrigin(origins = "*")
public class AssignedNurseController {

    @Autowired
    private AssignedNurseService AssignedNurseService;

    // Get all AssignedNurses
    @GetMapping
    public ResponseEntity<List<AssignedNurse>> getAllAssignedNurses() {
        List<AssignedNurse> AssignedNurses = AssignedNurseService.getAllAssignedNurses();
        return ResponseEntity.ok(AssignedNurses);
    }

    // Get AssignedNurse by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssignedNurse> getAssignedNurseById(@PathVariable Long id) {
        Optional<AssignedNurse> AssignedNurse = AssignedNurseService.getAssignedNurseById(id);
        return AssignedNurse.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new AssignedNurse
    @PostMapping("/create")
    public ResponseEntity<AssignedNurse> createAssignedNurse(@RequestBody AssignedNurse AssignedNurse) {
        try {
            AssignedNurse savedAssignedNurse = AssignedNurseService.saveAssignedNurse(AssignedNurse);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignedNurse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing AssignedNurse
    @PutMapping("/{id}")
    public ResponseEntity<AssignedNurse> updateAssignedNurse(@PathVariable Long id, @RequestBody AssignedNurse AssignedNurse) {
        Optional<AssignedNurse> existingAssignedNurse = AssignedNurseService.getAssignedNurseById(id);
        if (existingAssignedNurse.isPresent()) {
            AssignedNurse.setId(id); // Ensure the ID is set correctly
            AssignedNurse updatedAssignedNurse = AssignedNurseService.saveAssignedNurse(AssignedNurse);
            return ResponseEntity.ok(updatedAssignedNurse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a AssignedNurse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignedNurse(@PathVariable Long id) {
        Optional<AssignedNurse> existingAssignedNurse = AssignedNurseService.getAssignedNurseById(id);
        if (existingAssignedNurse.isPresent()) {
            AssignedNurseService.deleteAssignedNurse(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
