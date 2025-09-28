package com.example.hospital_backend.ClientNurseHistory;

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
@RequestMapping("/api/clientnursehistorys")
@CrossOrigin(origins = "*")
public class ClientNurseHistoryController {

    @Autowired
    private ClientNurseHistoryService ClientNurseHistoryService;

    // Get all ClientNurseHistorys
    @GetMapping
    public ResponseEntity<List<ClientNurseHistory>> getAllClientNurseHistorys() {
        List<ClientNurseHistory> ClientNurseHistorys = ClientNurseHistoryService.getAllClientNurseHistorys();
        return ResponseEntity.ok(ClientNurseHistorys);
    }

    // Get ClientNurseHistory by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientNurseHistory> getClientNurseHistoryById(@PathVariable Long id) {
        Optional<ClientNurseHistory> ClientNurseHistory = ClientNurseHistoryService.getClientNurseHistoryById(id);
        return ClientNurseHistory.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new ClientNurseHistory
    @PostMapping("/create")
    public ResponseEntity<ClientNurseHistory> createClientNurseHistory(@RequestBody ClientNurseHistory ClientNurseHistory) {
        try {
            ClientNurseHistory savedClientNurseHistory = ClientNurseHistoryService.saveClientNurseHistory(ClientNurseHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClientNurseHistory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing ClientNurseHistory
    @PutMapping("/{id}")
    public ResponseEntity<ClientNurseHistory> updateClientNurseHistory(@PathVariable Long id, @RequestBody ClientNurseHistory ClientNurseHistory) {
        Optional<ClientNurseHistory> existingClientNurseHistory = ClientNurseHistoryService.getClientNurseHistoryById(id);
        if (existingClientNurseHistory.isPresent()) {
            ClientNurseHistory.setId(id); // Ensure the ID is set correctly
            ClientNurseHistory updatedClientNurseHistory = ClientNurseHistoryService.saveClientNurseHistory(ClientNurseHistory);
            return ResponseEntity.ok(updatedClientNurseHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a ClientNurseHistory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientNurseHistory(@PathVariable Long id) {
        Optional<ClientNurseHistory> existingClientNurseHistory = ClientNurseHistoryService.getClientNurseHistoryById(id);
        if (existingClientNurseHistory.isPresent()) {
            ClientNurseHistoryService.deleteClientNurseHistory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
