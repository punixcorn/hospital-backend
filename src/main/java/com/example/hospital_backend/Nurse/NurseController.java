package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;

@RestController
@RequestMapping("/api/nurses")
@CrossOrigin(origins = "*")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    // Get all nurses
    @GetMapping
    public ResponseEntity<List<Nurse>> getAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();
        return ResponseEntity.ok(nurses);
    }

    // Get nurse by ID
    @GetMapping("/{id}")
    public ResponseEntity<NurseDTO> getNurseById(@PathVariable Long id) {
        Optional<NurseDTO> nurse = nurseService.getNurseById(id);
        if(nurse.isPresent())
        return nurse.get().map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new nurse
    @PostMapping("/create")
    public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) {
        try {
            Nurse savedNurse = nurseService.saveNurse(nurse);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNurse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing nurse
    @PutMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(@PathVariable Long id, @RequestBody Nurse nurse) {
        Optional<NurseDTO> existingNurse = nurseService.getNurseById(id);
        if (existingNurse.isPresent()) {
            nurse.setId(id); // Ensure the ID is set correctly
            Nurse updatedNurse = nurseService.saveNurse(nurse);
            return ResponseEntity.ok(updatedNurse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a nurse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        Optional<NurseDTO> existingNurse = nurseService.getNurseById(id);
        if (existingNurse.isPresent()) {
            nurseService.deleteNurse(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get nurses by availability
    @GetMapping("/available")
    public ResponseEntity<List<Nurse>> getNursesByAvailable(
            @RequestParam(name = "available", defaultValue = "true") Boolean available) {
        List<Nurse> nurses = nurseService.getNursesByAvailable(available);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by previous clients
    @PostMapping("/by-previous-client")
    public ResponseEntity<List<Nurse>> getNursesByPreviousClients(
            @RequestBody PreviousClient previousClient) {
        List<Nurse> nurses = nurseService.getNursesByPreviousClients(previousClient);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by certifications
    @PostMapping("/by-certifications")
    public ResponseEntity<List<Nurse>> getNursesByCertifications(
            @RequestBody List<Certification> certifications) {
        List<Nurse> nurses = nurseService.getNursesByCertifications(certifications);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by specializations
    @PostMapping("/by-specializations")
    public ResponseEntity<List<Nurse>> getNursesBySpecializations(
            @RequestBody List<Specialization> specializations) {
        List<Nurse> nurses = nurseService.getNursesBySpecializations(specializations);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by previous clients IDs
    @GetMapping("/by-previous-clients-ids")
    public ResponseEntity<List<Nurse>> getNursesByPreviousClientsId(
            @RequestParam("ids") List<Long> previousClientsIds) {
        List<Nurse> nurses = nurseService.getNursesByPreviousClientsId(previousClientsIds);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by assigned clients
    @PostMapping("/by-assigned-clients")
    public ResponseEntity<List<Nurse>> getNursesByAssignedClients(
            @RequestBody List<Client> assignedClients) {
        List<Nurse> nurses = nurseService.getNursesByAssignedClients(assignedClients);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by assigned clients IDs
    @GetMapping("/by-assigned-clients-ids")
    public ResponseEntity<List<Nurse>> getNursesByAssignedClientsId(
            @RequestParam("ids") List<Long> assignedClientsIds) {
        List<Nurse> nurses = nurseService.getNursesByAssignedClientsId(assignedClientsIds);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by end date
    @GetMapping("/by-end-date")
    public ResponseEntity<List<Nurse>> getNursesByEndDate(
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Nurse> nurses = nurseService.getNursesByEndDate(endDate);
        return ResponseEntity.ok(nurses);
    }

    // Get nurses by start date
    @GetMapping("/by-start-date")
    public ResponseEntity<List<Nurse>> getNursesByStartDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<Nurse> nurses = nurseService.getNursesByStartDate(startDate);
        return ResponseEntity.ok(nurses);
    }

    // Additional useful endpoints

    // Get nurses by date range
    @GetMapping("/by-date-range")
    public ResponseEntity<List<Nurse>> getNursesByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // You'll need to implement this in your service if needed
        List<Nurse> nursesByStart = nurseService.getNursesByStartDate(startDate);
        List<Nurse> nursesByEnd = nurseService.getNursesByEndDate(endDate);
        
        // Simple intersection (you might want to implement this logic in service)
        nursesByStart.retainAll(nursesByEnd);
        return ResponseEntity.ok(nursesByStart);
    }

    // Search nurses (you can implement this in service if needed)
    @GetMapping("/search")
    public ResponseEntity<List<Nurse>> searchNurses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<Nurse> nurses = nurseService.getAllNurses();
        
        // Apply filters (implement proper filtering logic in service layer)
        if (available != null) {
            nurses = nurseService.getNursesByAvailable(available);
        }
        
        return ResponseEntity.ok(nurses);
    }
}

/* 
    
    @Autowired
    private NurseRepository nurseRepository;
    
    @GetMapping()
    public Iterable<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Nurse> getNurseById(@PathVariable Long id) {
        return nurseRepository.findById(id);
    }
    
    @PostMapping("/add")
    public Nurse addNurse(@RequestBody Nurse nurse) {
        return nurseRepository.save(nurse);
    }
    
    @PutMapping("/{id}")
    public Nurse updateNurse(@PathVariable Long id, @RequestBody Nurse nurse) {
        nurse.setId(id);
        return nurseRepository.save(nurse);
    }
    
    @DeleteMapping("/{id}")
    public void deleteNurse(@PathVariable Long id) {
        nurseRepository.deleteById(id);
    }
}
*/