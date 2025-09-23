package com.example.hospital_backend.AssessmentInfo;

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
@RequestMapping("/api/AssessmentInfos")
@CrossOrigin(origins = "*")
public class AssessmentInfoController {

    @Autowired
    private AssessmentInfoService AssessmentInfoService;

    // Get all AssessmentInfos
    @GetMapping
    public ResponseEntity<List<AssessmentInfo>> getAllAssessmentInfos() {
        List<AssessmentInfo> AssessmentInfos = AssessmentInfoService.getAllAssessmentInfos();
        return ResponseEntity.ok(AssessmentInfos);
    }

    // Get AssessmentInfo by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentInfo> getAssessmentInfoById(@PathVariable Long id) {
        Optional<AssessmentInfo> AssessmentInfo = AssessmentInfoService.getAssessmentInfoById(id);
        return AssessmentInfo.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new AssessmentInfo
    @PostMapping("/create")
    public ResponseEntity<AssessmentInfo> createAssessmentInfo(@RequestBody AssessmentInfo AssessmentInfo) {
        try {
            AssessmentInfo savedAssessmentInfo = AssessmentInfoService.saveAssessmentInfo(AssessmentInfo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAssessmentInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing AssessmentInfo
    @PutMapping("/{id}")
    public ResponseEntity<AssessmentInfo> updateAssessmentInfo(@PathVariable Long id, @RequestBody AssessmentInfo AssessmentInfo) {
        Optional<AssessmentInfo> existingAssessmentInfo = AssessmentInfoService.getAssessmentInfoById(id);
        if (existingAssessmentInfo.isPresent()) {
            AssessmentInfo.setId(id); // Ensure the ID is set correctly
            AssessmentInfo updatedAssessmentInfo = AssessmentInfoService.saveAssessmentInfo(AssessmentInfo);
            return ResponseEntity.ok(updatedAssessmentInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a AssessmentInfo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessmentInfo(@PathVariable Long id) {
        Optional<AssessmentInfo> existingAssessmentInfo = AssessmentInfoService.getAssessmentInfoById(id);
        if (existingAssessmentInfo.isPresent()) {
            AssessmentInfoService.deleteAssessmentInfo(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
