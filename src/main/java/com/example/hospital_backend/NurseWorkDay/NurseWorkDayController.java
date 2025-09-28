package com.example.hospital_backend.NurseWorkDay;

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
@RequestMapping("/api/nurseworkdays")
@CrossOrigin(origins = "*")
public class NurseWorkDayController {

    @Autowired
    private NurseWorkDayService NurseWorkDayService;

    // Get all NurseWorkDays
    @GetMapping
    public ResponseEntity<List<NurseWorkDay>> getAllNurseWorkDays() {
        List<NurseWorkDay> NurseWorkDays = NurseWorkDayService.getAllNurseWorkDays();
        return ResponseEntity.ok(NurseWorkDays);
    }

    // Get NurseWorkDay by ID
    @GetMapping("/{id}")
    public ResponseEntity<NurseWorkDay> getNurseWorkDayById(@PathVariable Long id) {
        Optional<NurseWorkDay> NurseWorkDay = NurseWorkDayService.getNurseWorkDayById(id);
        return NurseWorkDay.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new NurseWorkDay
    @PostMapping("/create")
    public ResponseEntity<NurseWorkDay> createNurseWorkDay(@RequestBody NurseWorkDay NurseWorkDay) {
        try {
            NurseWorkDay savedNurseWorkDay = NurseWorkDayService.saveNurseWorkDay(NurseWorkDay);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNurseWorkDay);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing NurseWorkDay
    @PutMapping("/{id}")
    public ResponseEntity<NurseWorkDay> updateNurseWorkDay(@PathVariable Long id, @RequestBody NurseWorkDay NurseWorkDay) {
        Optional<NurseWorkDay> existingNurseWorkDay = NurseWorkDayService.getNurseWorkDayById(id);
        if (existingNurseWorkDay.isPresent()) {
            NurseWorkDay.setId(id); // Ensure the ID is set correctly
            NurseWorkDay updatedNurseWorkDay = NurseWorkDayService.saveNurseWorkDay(NurseWorkDay);
            return ResponseEntity.ok(updatedNurseWorkDay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a NurseWorkDay
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurseWorkDay(@PathVariable Long id) {
        Optional<NurseWorkDay> existingNurseWorkDay = NurseWorkDayService.getNurseWorkDayById(id);
        if (existingNurseWorkDay.isPresent()) {
            NurseWorkDayService.deleteNurseWorkDay(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
