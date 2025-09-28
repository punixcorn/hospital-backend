package com.example.hospital_backend.Client;

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

/**
 * REST Controller for Client management
 * 
 * Provides endpoints for:
 * - Creating, reading, updating, and deleting clients
 * - Searching clients by various criteria
 * - Managing client information and assignments
 * 
 * Access: ADMIN role only
 */
@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Get all clients
     * 
     * @return List of all clients with their basic information
     */
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    /**
     * Get client by ID
     * 
     * @param id Client ID (must be positive)
     * @return Client details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return clientService.findById(id)
            .map(client -> ResponseEntity.ok(client))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new client
     * 
     * Required fields:
     * - name: Client's full name
     * - status: Client status (Active, Pending, Inactive, etc.)
     * - phone: Unique phone number
     * 
     * @param request CreateClientRequest with client details
     * @return Created client or error message
     */
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody CreateClientRequest request) {
        try {
            ClientDTO createdClient = clientService.createClient(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create client: " + e.getMessage()));
        }
    }

    /**
     * Update an existing client
     * 
     * @param id Client ID (must be positive)
     * @param request UpdateClientRequest with fields to update
     * @return Updated client or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id, 
            @RequestBody UpdateClientRequest request) {
        try {
            ClientDTO updatedClient = clientService.updateClient(id, request);
            return ResponseEntity.ok(updatedClient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to update client: " + e.getMessage()));
        }
    }

    /**
     * Delete a client
     * 
     * @param id Client ID (must be positive)
     * @return 204 No Content on success, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete client: " + e.getMessage()));
        }
    }

    /**
     * Get clients by status
     * 
     * @param status Client status (Active, Pending, Inactive, etc.)
     * @return List of clients with the specified status
     */
    @GetMapping("/status")
    public ResponseEntity<List<ClientDTO>> findByStatus(@RequestParam String status) {
        List<ClientDTO> clients = clientService.findByStatus(status);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by service type
     * 
     * @param serviceType Type of service (Home Care, Rehabilitation, etc.)
     * @return List of clients with the specified service type
     */
    @GetMapping("/service-type")
    public ResponseEntity<List<ClientDTO>> findByServiceType(@RequestParam String serviceType) {
        List<ClientDTO> clients = clientService.findByServiceType(serviceType);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by age
     * 
     * @param age Client age
     * @return List of clients with the specified age
     */
    @GetMapping("/age")
    public ResponseEntity<List<ClientDTO>> findByAge(@RequestParam Integer age) {
        List<ClientDTO> clients = clientService.findByAge(age);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by deceased status
     * 
     * @param deceased Whether client is deceased
     * @return List of clients with the specified deceased status
     */
    @GetMapping("/deceased")
    public ResponseEntity<List<ClientDTO>> findByDeceased(@RequestParam Boolean deceased) {
        List<ClientDTO> clients = clientService.findByDeceased(deceased);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by assigned nurse IDs
     * 
     * @param nurseIds List of nurse IDs
     * @return List of clients assigned to the specified nurses
     */
    // @GetMapping("/assigned-nurses")
    // public ResponseEntity<List<ClientDTO>> findByAssignedNurseIds(@RequestParam List<Long> nurseIds) {
    //     List<ClientDTO> clients = clientService.findByAssignedNurseIds(nurseIds);
    //     return ResponseEntity.ok(clients);
    // }

    /**
     * Get clients by medication IDs
     * 
     * @param medicationIds List of medication IDs
     * @return List of clients taking the specified medications
     */
    @GetMapping("/medications/{id}")
    public ResponseEntity<List<Long>> getMedecations(@PathVariable Long id) {
        List<Long> medicationIdsList = clientService.findAllMedicationIdsByClientId(id);
        return ResponseEntity.ok(medicationIdsList);
    }

    /**
     * Get clients by insurance provider
     * 
     * @param insurance Insurance provider name
     * @return List of clients with the specified insurance
     */
    @GetMapping("/insurance")
    public ResponseEntity<List<ClientDTO>> findByInsurance(@RequestParam String insurance) {
        List<ClientDTO> clients = clientService.findByInsurance(insurance);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get client by phone number
     * 
     * @param phone Phone number
     * @return Client with the specified phone number or 404 if not found
     */
    @GetMapping("/phone")
    public ResponseEntity<?> findByPhone(@RequestParam String phone) {
        return clientService.findByPhone(phone)
            .map(client -> ResponseEntity.ok(client))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get clients by medical condition
     * 
     * @param condition Medical condition
     * @return List of clients with the specified condition
     */
    @GetMapping("/condition")
    public ResponseEntity<List<ClientDTO>> findByCondition(@RequestParam String condition) {
        List<ClientDTO> clients = clientService.findByCondition(condition);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by location
     * 
     * @param location Client location/address
     * @return List of clients at the specified location
     */
    @GetMapping("/location")
    public ResponseEntity<List<ClientDTO>> findByLocation(@RequestParam String location) {
        List<ClientDTO> clients = clientService.findByLocation(location);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by name
     * 
     * @param name Client name
     * @return List of clients with the specified name
     */
    @GetMapping("/name")
    public ResponseEntity<List<ClientDTO>> findByName(@RequestParam String name) {
        List<ClientDTO> clients = clientService.findByName(name);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by date reachout
     * 
     * @param dateReachout Date when client first reached out
     * @return List of clients who reached out on the specified date
     */
    @GetMapping("/date-reachout")
    public ResponseEntity<List<ClientDTO>> findByDateReachout(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateReachout) {
        List<ClientDTO> clients = clientService.findByDateReachout(dateReachout);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get clients by date wanted
     * 
     * @param dateWanted Date when client wants service to start
     * @return List of clients wanting service on the specified date
     */
    @GetMapping("/date-wanted")
    public ResponseEntity<List<ClientDTO>> findByDateWanted(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateWanted) {
        List<ClientDTO> clients = clientService.findByDateWanted(dateWanted);
        return ResponseEntity.ok(clients);
    }

    /**
     * Search clients by name containing substring (case insensitive)
     * 
     * @param name Name substring to search for
     * @return List of clients whose names contain the substring
     */
    @GetMapping("/name/search")
    public ResponseEntity<List<ClientDTO>> findByNameContaining(@RequestParam String name) {
        List<ClientDTO> clients = clientService.findByNameContaining(name);
        return ResponseEntity.ok(clients);
    }

    /**
     * Search clients by location containing substring (case insensitive)
     * 
     * @param location Location substring to search for
     * @return List of clients whose locations contain the substring
     */
    @GetMapping("/location/search")
    public ResponseEntity<List<ClientDTO>> findByLocationContaining(@RequestParam String location) {
        List<ClientDTO> clients = clientService.findByLocationContaining(location);
        return ResponseEntity.ok(clients);
    }

    /**
     * Search clients by condition containing substring (case insensitive)
     * 
     * @param condition Condition substring to search for
     * @return List of clients whose conditions contain the substring
     */
    @GetMapping("/condition/search")
    public ResponseEntity<List<ClientDTO>> findByConditionContaining(@RequestParam String condition) {
        List<ClientDTO> clients = clientService.findByConditionContaining(condition);
        return ResponseEntity.ok(clients);
    }

    /**
     * Get active clients only
     * 
     * @return List of active clients
     */
    @GetMapping("/active")
    public ResponseEntity<List<ClientDTO>> getActiveClients() {
        List<ClientDTO> clients = clientService.getActiveClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Get pending clients only
     * 
     * @return List of pending clients
     */
    @GetMapping("/pending")
    public ResponseEntity<List<ClientDTO>> getPendingClients() {
        List<ClientDTO> clients = clientService.getPendingClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Get inactive clients only
     * 
     * @return List of inactive clients
     */
    @GetMapping("/inactive")
    public ResponseEntity<List<ClientDTO>> getInactiveClients() {
        List<ClientDTO> clients = clientService.getInactiveClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Get living clients only (not deceased)
     * 
     * @return List of living clients
     */
    @GetMapping("/living")
    public ResponseEntity<List<ClientDTO>> getLivingClients() {
        List<ClientDTO> clients = clientService.getLivingClients();
        return ResponseEntity.ok(clients);
    }
}