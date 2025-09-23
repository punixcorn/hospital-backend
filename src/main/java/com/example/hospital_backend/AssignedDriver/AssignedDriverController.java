package com.example.hospital_backend.AssignedDriver;

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
@RequestMapping("/api/AssignedDrivers")
@CrossOrigin(origins = "*")
public class AssignedDriverController {

    @Autowired
    private AssignedDriverService AssignedDriverService;

    // Get all AssignedDrivers
    @GetMapping
    public ResponseEntity<List<AssignedDriver>> getAllAssignedDrivers() {
        List<AssignedDriver> AssignedDrivers = AssignedDriverService.getAllAssignedDrivers();
        return ResponseEntity.ok(AssignedDrivers);
    }

    // Get AssignedDriver by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssignedDriver> getAssignedDriverById(@PathVariable Long id) {
        Optional<AssignedDriver> AssignedDriver = AssignedDriverService.getAssignedDriverById(id);
        return AssignedDriver.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new AssignedDriver
    @PostMapping("/create")
    public ResponseEntity<AssignedDriver> createAssignedDriver(@RequestBody AssignedDriver AssignedDriver) {
        try {
            AssignedDriver savedAssignedDriver = AssignedDriverService.saveAssignedDriver(AssignedDriver);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignedDriver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing AssignedDriver
    @PutMapping("/{id}")
    public ResponseEntity<AssignedDriver> updateAssignedDriver(@PathVariable Long id, @RequestBody AssignedDriver AssignedDriver) {
        Optional<AssignedDriver> existingAssignedDriver = AssignedDriverService.getAssignedDriverById(id);
        if (existingAssignedDriver.isPresent()) {
            AssignedDriver.setId(id); // Ensure the ID is set correctly
            AssignedDriver updatedAssignedDriver = AssignedDriverService.saveAssignedDriver(AssignedDriver);
            return ResponseEntity.ok(updatedAssignedDriver);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a AssignedDriver
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignedDriver(@PathVariable Long id) {
        Optional<AssignedDriver> existingAssignedDriver = AssignedDriverService.getAssignedDriverById(id);
        if (existingAssignedDriver.isPresent()) {
            AssignedDriverService.deleteAssignedDriver(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
