package com.example.hospital_backend.PreviousClient;

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
@RequestMapping("/api/PreviousClients")
@CrossOrigin(origins = "*")
public class PreviousClientController {

    @Autowired
    private PreviousClientService PreviousClientService;

    // Get all PreviousClients
    @GetMapping
    public ResponseEntity<List<PreviousClient>> getAllPreviousClients() {
        List<PreviousClient> PreviousClients = PreviousClientService.getAllPreviousClients();
        return ResponseEntity.ok(PreviousClients);
    }

    // Get PreviousClient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PreviousClient> getPreviousClientById(@PathVariable Long id) {
        Optional<PreviousClient> PreviousClient = PreviousClientService.getPreviousClientById(id);
        return PreviousClient.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new PreviousClient
    @PostMapping("/create")
    public ResponseEntity<PreviousClient> createPreviousClient(@RequestBody PreviousClient PreviousClient) {
        try {
            PreviousClient savedPreviousClient = PreviousClientService.savePreviousClient(PreviousClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPreviousClient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing PreviousClient
    @PutMapping("/{id}")
    public ResponseEntity<PreviousClient> updatePreviousClient(@PathVariable Long id, @RequestBody PreviousClient PreviousClient) {
        Optional<PreviousClient> existingPreviousClient = PreviousClientService.getPreviousClientById(id);
        if (existingPreviousClient.isPresent()) {
            PreviousClient.setId(id); // Ensure the ID is set correctly
            PreviousClient updatedPreviousClient = PreviousClientService.savePreviousClient(PreviousClient);
            return ResponseEntity.ok(updatedPreviousClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a PreviousClient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreviousClient(@PathVariable Long id) {
        Optional<PreviousClient> existingPreviousClient = PreviousClientService.getPreviousClientById(id);
        if (existingPreviousClient.isPresent()) {
            PreviousClientService.deletePreviousClient(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
