package com.example.hospital_backend.Driver;

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
@RequestMapping("/api/Drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private DriverService DriverService;

    // Get all Drivers
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> Drivers = DriverService.getAllDrivers();
        return ResponseEntity.ok(Drivers);
    }

    // Get Driver by ID
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Optional<Driver> Driver = DriverService.getDriverById(id);
        return Driver.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Driver
    @PostMapping("/create")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver Driver) {
        try {
            Driver savedDriver = DriverService.saveDriver(Driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Driver
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver Driver) {
        Optional<Driver> existingDriver = DriverService.getDriverById(id);
        if (existingDriver.isPresent()) {
            Driver.setId(id); // Ensure the ID is set correctly
            Driver updatedDriver = DriverService.saveDriver(Driver);
            return ResponseEntity.ok(updatedDriver);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Driver
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        Optional<Driver> existingDriver = DriverService.getDriverById(id);
        if (existingDriver.isPresent()) {
            DriverService.deleteDriver(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
