package com.example.hospital_backend.HourlyData;

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
@RequestMapping("/api/hourlydatas")
@CrossOrigin(origins = "*")
public class HourlyDataController {

    @Autowired
    private HourlyDataService HourlyDataService;

    // Get all HourlyDatas
    @GetMapping
    public ResponseEntity<List<HourlyData>> getAllHourlyDatas() {
        List<HourlyData> HourlyDatas = HourlyDataService.getAllHourlyDatas();
        return ResponseEntity.ok(HourlyDatas);
    }

    // Get HourlyData by ID
    @GetMapping("/{id}")
    public ResponseEntity<HourlyData> getHourlyDataById(@PathVariable Long id) {
        Optional<HourlyData> HourlyData = HourlyDataService.getHourlyDataById(id);
        return HourlyData.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new HourlyData
    @PostMapping("/create")
    public ResponseEntity<HourlyData> createHourlyData(@RequestBody HourlyData HourlyData) {
        try {
            HourlyData savedHourlyData = HourlyDataService.saveHourlyData(HourlyData);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedHourlyData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing HourlyData
    @PutMapping("/{id}")
    public ResponseEntity<HourlyData> updateHourlyData(@PathVariable Long id, @RequestBody HourlyData HourlyData) {
        Optional<HourlyData> existingHourlyData = HourlyDataService.getHourlyDataById(id);
        if (existingHourlyData.isPresent()) {
            HourlyData.setId(id); // Ensure the ID is set correctly
            HourlyData updatedHourlyData = HourlyDataService.saveHourlyData(HourlyData);
            return ResponseEntity.ok(updatedHourlyData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a HourlyData
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHourlyData(@PathVariable Long id) {
        Optional<HourlyData> existingHourlyData = HourlyDataService.getHourlyDataById(id);
        if (existingHourlyData.isPresent()) {
            HourlyDataService.deleteHourlyData(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
