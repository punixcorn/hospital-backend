package com.example.hospital_backend.ClientMedication;

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
@RequestMapping("/api/clientmedications")
@CrossOrigin(origins = "*")
public class ClientMedicationController {

    @Autowired
    private ClientMedicationService ClientMedicationService;

    // Get all ClientMedications
    @GetMapping
    public ResponseEntity<List<ClientMedication>> getAllClientMedications() {
        List<ClientMedication> ClientMedications = ClientMedicationService.getAllClientMedications();
        return ResponseEntity.ok(ClientMedications);
    }

    // Get ClientMedication by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientMedication> getClientMedicationById(@PathVariable Long id) {
        Optional<ClientMedication> ClientMedication = ClientMedicationService.getClientMedicationById(id);
        return ClientMedication.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new ClientMedication
    @PostMapping("/create")
    public ResponseEntity<ClientMedication> createClientMedication(@RequestBody ClientMedication ClientMedication) {
        try {
            ClientMedication savedClientMedication = ClientMedicationService.saveClientMedication(ClientMedication);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClientMedication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing ClientMedication
    @PutMapping("/{id}")
    public ResponseEntity<ClientMedication> updateClientMedication(@PathVariable Long id, @RequestBody ClientMedication ClientMedication) {
        Optional<ClientMedication> existingClientMedication = ClientMedicationService.getClientMedicationById(id);
        if (existingClientMedication.isPresent()) {
            ClientMedication.setId(id); // Ensure the ID is set correctly
            ClientMedication updatedClientMedication = ClientMedicationService.saveClientMedication(ClientMedication);
            return ResponseEntity.ok(updatedClientMedication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a ClientMedication
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientMedication(@PathVariable Long id) {
        Optional<ClientMedication> existingClientMedication = ClientMedicationService.getClientMedicationById(id);
        if (existingClientMedication.isPresent()) {
            ClientMedicationService.deleteClientMedication(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
