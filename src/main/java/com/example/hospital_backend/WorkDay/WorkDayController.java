package com.example.hospital_backend.WorkDay;

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
@RequestMapping("/api/WorkDays")
@CrossOrigin(origins = "*")
public class WorkDayController {

    @Autowired
    private WorkDayService WorkDayService;

    // Get all WorkDays
    @GetMapping
    public ResponseEntity<List<WorkDay>> getAllWorkDays() {
        List<WorkDay> WorkDays = WorkDayService.getAllWorkDays();
        return ResponseEntity.ok(WorkDays);
    }

    // Get WorkDay by ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkDay> getWorkDayById(@PathVariable Long id) {
        Optional<WorkDay> WorkDay = WorkDayService.getWorkDayById(id);
        return WorkDay.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new WorkDay
    @PostMapping("/create")
    public ResponseEntity<WorkDay> createWorkDay(@RequestBody WorkDay WorkDay) {
        try {
            WorkDay savedWorkDay = WorkDayService.saveWorkDay(WorkDay);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkDay);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing WorkDay
    @PutMapping("/{id}")
    public ResponseEntity<WorkDay> updateWorkDay(@PathVariable Long id, @RequestBody WorkDay WorkDay) {
        Optional<WorkDay> existingWorkDay = WorkDayService.getWorkDayById(id);
        if (existingWorkDay.isPresent()) {
            WorkDay.setId(id); // Ensure the ID is set correctly
            WorkDay updatedWorkDay = WorkDayService.saveWorkDay(WorkDay);
            return ResponseEntity.ok(updatedWorkDay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a WorkDay
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkDay(@PathVariable Long id) {
        Optional<WorkDay> existingWorkDay = WorkDayService.getWorkDayById(id);
        if (existingWorkDay.isPresent()) {
            WorkDayService.deleteWorkDay(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
