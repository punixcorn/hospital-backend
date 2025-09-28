package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;


import lombok.Value;

/**
 * REST Controller for Nurse management
 * 
 * Provides endpoints for:
 * - Creating, reading, updating, and deleting nurses
 * - Searching nurses by various criteria
 * - Managing nurse availability and assignments
 * 
 * Access: ADMIN and NURSE roles
 */
@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
@Validated
public class NurseController {

    @Autowired
    private NurseService nurseService;

    /**
     * Get all nurses
     * 
     * @return List of all nurses with their basic information
     */
    @GetMapping
    public ResponseEntity<List<NurseDTO>> getAllNurses() {
        List<NurseDTO> nurses = nurseService.getAllNurses();
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurse by ID
     * 
     * @param id Nurse ID (must be positive)
     * @return Nurse details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNurseById(@PathVariable Long id) {
        return nurseService.getNurseById(id)
            .map(nurse -> ResponseEntity.ok(nurse))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new nurse
     * 
     * Required fields:
     * - userId: Must reference an existing user with NURSE role
     * - age: Between 18-80
     * - height: Between 100-250 cm
     * - experience: Non-negative
     * - startDate: Today or future
     * - endDate: After startDate
     * - payRate: Positive
     * - payPerHour: Positive
     * 
     * @param request CreateNurseRequest with nurse details
     * @return Created nurse or error message
     */
    @PostMapping
    public ResponseEntity<?> createNurse(@RequestBody CreateNurseRequest request) {
        try {
            NurseDTO createdNurse = nurseService.createNurse(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNurse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create nurse: " + e.getMessage()));
        }
    }

    /**
     * Update an existing nurse
     * 
     * @param id Nurse ID (must be positive)
     * @param request UpdateNurseRequest with fields to update
     * @return Updated nurse or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNurse(
            @PathVariable Long id, 
            @RequestBody UpdateNurseRequest request) {
        try {
            NurseDTO updatedNurse = nurseService.updateNurse(id, request);
            return ResponseEntity.ok(updatedNurse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to update nurse: " + e.getMessage()));
        }
    }

    /**
     * Delete a nurse
     * 
     * @param id Nurse ID (must be positive)
     * @return 204 No Content on success, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNurse(@PathVariable  Long id) {
        try {
            nurseService.deleteNurse(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete nurse: " + e.getMessage()));
        }
    }

    /**
     * Get nurses by availability status
     * 
     * @param available true for available nurses, false for unavailable
     * @return List of nurses matching availability status
     */
    @GetMapping("/available")
    public ResponseEntity<List<NurseDTO>> getNursesByAvailable(
            @RequestParam(name = "available", defaultValue = "true") Boolean available) {
        List<NurseDTO> nurses = nurseService.getNursesByAvailable(available);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get available nurses only
     * 
     * @return List of available nurses
     */
    @GetMapping("/available-only")
    public ResponseEntity<List<NurseDTO>> getAvailableNurses() {
        List<NurseDTO> nurses = nurseService.getAvailableNurses();
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by minimum experience
     * 
     * @param minExperience Minimum years of experience
     * @return List of nurses with at least the specified experience
     */
    @GetMapping("/by-experience")
    public ResponseEntity<List<NurseDTO>> getNursesByMinExperience(
            @RequestParam("minExperience")  Integer minExperience) {
        List<NurseDTO> nurses = nurseService.getNursesByMinExperience(minExperience);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by pay rate range
     * 
     * @param minPayRate Minimum pay rate
     * @param maxPayRate Maximum pay rate
     * @return List of nurses within the pay rate range
     */
    @GetMapping("/by-pay-rate")
    public ResponseEntity<List<NurseDTO>> getNursesByPayRateRange(
            @RequestParam("minPayRate") Double minPayRate,
            @RequestParam("maxPayRate") Double maxPayRate) {
        List<NurseDTO> nurses = nurseService.getNursesByPayRateRange(minPayRate, maxPayRate);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by previous clients
     * 
     * @param previousClient PreviousClient object to search by
     * @return List of nurses who have worked with this client
     */
    @PostMapping("/by-previous-client")
    public ResponseEntity<List<NurseDTO>> getNursesByPreviousClients(
            @RequestBody PreviousClient previousClient) {
        List<NurseDTO> nurses = nurseService.getNursesByPreviousClients(previousClient);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by certifications
     * 
     * @param certifications List of certifications to search by
     * @return List of nurses with any of the specified certifications
     */
    @PostMapping("/by-certifications")
    public ResponseEntity<List<NurseDTO>> getNursesByCertifications(
            @RequestBody List<Certification> certifications) {
        List<NurseDTO> nurses = nurseService.getNursesByCertifications(certifications);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by specializations
     * 
     * @param specializations List of specializations to search by
     * @return List of nurses with any of the specified specializations
     */
    @PostMapping("/by-specializations")
    public ResponseEntity<List<NurseDTO>> getNursesBySpecializations(
            @RequestBody List<Specialization> specializations) {
        List<NurseDTO> nurses = nurseService.getNursesBySpecializations(specializations);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by previous client IDs
     * 
     * @param previousClientsIds List of previous client IDs
     * @return List of nurses who have worked with any of these clients
     */
    @GetMapping("/by-previous-clients-ids")
    public ResponseEntity<List<NurseDTO>> getNursesByPreviousClientsId(
            @RequestParam("ids") List<Long> previousClientsIds) {
        List<NurseDTO> nurses = nurseService.getNursesByPreviousClientsId(previousClientsIds);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by assigned clients
     * 
     * @param assignedClients List of clients to search by
     * @return List of nurses currently assigned to any of these clients
     */
    @PostMapping("/by-assigned-clients")
    public ResponseEntity<List<NurseDTO>> getNursesByAssignedClients(
            @RequestBody List<Client> assignedClients) {
        List<NurseDTO> nurses = nurseService.getNursesByAssignedClients(assignedClients);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by assigned client IDs
     * 
     * @param assignedClientsIds List of assigned client IDs
     * @return List of nurses currently assigned to any of these clients
     */
    @GetMapping("/by-assigned-clients-ids")
    public ResponseEntity<List<NurseDTO>> getNursesByAssignedClientsId(
            @RequestParam("ids") List<Long> assignedClientsIds) {
        List<NurseDTO> nurses = nurseService.getNursesByAssignedClientsId(assignedClientsIds);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by end date
     * 
     * @param endDate End date to search by
     * @return List of nurses ending on this date
     */
    @GetMapping("/by-end-date")
    public ResponseEntity<List<NurseDTO>> getNursesByEndDate(
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<NurseDTO> nurses = nurseService.getNursesByEndDate(endDate);
        return ResponseEntity.ok(nurses);
    }

    /**
     * Get nurses by start date
     * 
     * @param startDate Start date to search by
     * @return List of nurses starting on this date
     */
    @GetMapping("/by-start-date")
    public ResponseEntity<List<NurseDTO>> getNursesByStartDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<NurseDTO> nurses = nurseService.getNursesByStartDate(startDate);
        return ResponseEntity.ok(nurses);
    }

}