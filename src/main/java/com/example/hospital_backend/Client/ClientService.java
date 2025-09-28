package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.hospital_backend.Item.Item;
import com.example.hospital_backend.Item.ItemRepository;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Get all clients with their basic information
     */
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get client by ID with full details
     */
    public Optional<ClientDTO> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return clientRepository.findById(id)
            .flatMap(Client::toClientDTO);
    }

    /**
     * Create a new client with validation
     */
    public ClientDTO createClient(CreateClientRequest request) {
        // Validate required fields
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name is required");
        }
        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Client status is required");
        }
        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Client phone is required");
        }
        
        // Check if phone number already exists
        if (clientRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists: " + request.getPhone());
        }
        
        // Generate unique client link ID if not provided
        String clientLinkID = UUID.randomUUID().toString();
        
        // Build client entity
        Client client = Client.builder()
            .name(request.getName().trim())
            .status(request.getStatus().trim())
            .location(request.getLocation())
            .coordinatesLat(request.getCoordinatesLat())
            .coordinatesLng(request.getCoordinatesLng())
            .message(request.getMessage())
            .description(request.getDescription())
            .dateReachout(request.getDateReachout())
            .dateWanted(request.getDateWanted())
            .serviceType(request.getServiceType())
            .clientLinkID(clientLinkID)
            .age(request.getAge())
            .height(request.getHeight())
            .phone(request.getPhone().trim())
            .deceased(request.getDeceased())
            .condition(request.getCondition())
            .insurance(request.getInsurance())
            .emergencyContact(request.getEmergencyContact())
            .build();
        
        // Add medications if provided
        // if (request.getMedicationIds() != null && !request.getMedicationIds().isEmpty()) {
        //     List<Medication> medications = medicationRepository.findAllById(request.getMedicationIds());
        //     client.setMedications(medications);
        // }
        
        Client savedClient = clientRepository.save(client);
        return savedClient.toClientDTO().orElseThrow(() -> new RuntimeException("Failed to convert client to DTO"));
    }

    /**
     * Update an existing client
     */
    public ClientDTO updateClient(Long id, UpdateClientRequest request) {
        Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + id));
        
        // Update fields if provided
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            existingClient.setName(request.getName().trim());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            existingClient.setStatus(request.getStatus().trim());
        }
        if (request.getLocation() != null) {
            existingClient.setLocation(request.getLocation());
        }
        if (request.getCoordinatesLat() != null) {
            existingClient.setCoordinatesLat(request.getCoordinatesLat());
        }
        if (request.getCoordinatesLng() != null) {
            existingClient.setCoordinatesLng(request.getCoordinatesLng());
        }
        if (request.getMessage() != null) {
            existingClient.setMessage(request.getMessage());
        }
        if (request.getDescription() != null) {
            existingClient.setDescription(request.getDescription());
        }
        if (request.getDateReachout() != null) {
            existingClient.setDateReachout(request.getDateReachout());
        }
        if (request.getDateWanted() != null) {
            existingClient.setDateWanted(request.getDateWanted());
        }
        if (request.getServiceType() != null) {
            existingClient.setServiceType(request.getServiceType());
        }
        if (request.getAge() != null) {
            existingClient.setAge(request.getAge());
        }
        if (request.getHeight() != null) {
            existingClient.setHeight(request.getHeight());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            // Check if phone number already exists for different client
            Optional<Client> clientWithPhone = clientRepository.findByPhone(request.getPhone().trim());
            if (clientWithPhone.isPresent() && !clientWithPhone.get().getId().equals(id)) {
                throw new IllegalArgumentException("Phone number already exists: " + request.getPhone());
            }
            existingClient.setPhone(request.getPhone().trim());
        }
        if (request.getDeceased() != null) {
            existingClient.setDeceased(request.getDeceased());
        }
        if (request.getCondition() != null) {
            existingClient.setCondition(request.getCondition());
        }
        if (request.getInsurance() != null) {
            existingClient.setInsurance(request.getInsurance());
        }
        if (request.getEmergencyContact() != null) {
            existingClient.setEmergencyContact(request.getEmergencyContact());
        }
        
        // Update medications if provided
        // if (request.getMedication_ids() != null) {
        //     List<Medication> medications = medicationRepository.findAllById(request.getMedication_ids());
        //     existingClient.setMedications(medications);
        // }

        if (request.getItem_ids() != null) {
                // check if item exists already or is inventory
                List<Long> client_items = existingClient.getItems();
                if (client_items.isEmpty()) {
                   existingClient.setItems(request.getItem_ids()); 
                } else {
                    List<Long> new_items = new ArrayList<>();
                    for (Long item_id : request.getItem_ids()) {
                        if (!client_items.contains(item_id)) {
                            Item item = itemRepository.findById(item_id).get();
                            new_items.add(item_id);
                        }
                    }
                    client_items.removeAll(new_items);
                    for (Long item_id : client_items) {
                        if (!new_items.contains(item_id)) {
                            itemRepository.deleteById(item_id);
                        }
                    }
                    existingClient.setItems(new_items);
                }
            }
        
    


        Client updatedClient = clientRepository.save(existingClient);
        return updatedClient.toClientDTO().orElseThrow(() -> new RuntimeException("Failed to convert client to DTO"));
    }

    /**
     * Delete a client
     */
    public void deleteClient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client not found with ID: " + id);
        }
        
        clientRepository.deleteById(id);
    }

    /**
     * Get clients by status
     */
    public List<ClientDTO> findByStatus(String status) {
        List<Client> clients = clientRepository.findByStatus(status);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by service type
     */
    public List<ClientDTO> findByServiceType(String serviceType) {
        List<Client> clients = clientRepository.findByServiceType(serviceType);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by age
     */
    public List<ClientDTO> findByAge(Integer age) {
        List<Client> clients = clientRepository.findByAge(age);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by deceased status
     */
    public List<ClientDTO> findByDeceased(Boolean deceased) {
        List<Client> clients = clientRepository.findByDeceased(deceased);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by assigned nurse IDs
     */
    // public List<ClientDTO> findByAssignedNurseIds(List<Long> nurseIds) {
    //     List<Client> clients = clientRepository.findByAssignedNurseIds(nurseIds);
    //     return clients.stream()
    //         .map(client -> client.toClientDTO().orElse(null))
    //         .filter(dto -> dto != null)
    //         .toList();
    // }

    /**
     * Get clients by medication IDs
     */
    // public List<ClientDTO> findByMedicationIds(List<Long> medicationIds) {
    //     List<Client> clients = clientRepository.findByMedicationIds(medicationIds);
    //     return clients.stream()
    //         .map(client -> client.toClientDTO().orElse(null))
    //         .filter(dto -> dto != null)
    //         .toList();
    // }


    public List<Long> findAllMedicationIdsByClientId(Long clientId) {
        return clientRepository.findAllMedicationIdsById(clientId);
    }

    /**
     * Get clients by insurance
     */
    public List<ClientDTO> findByInsurance(String insurance) {
        List<Client> clients = clientRepository.findByInsurance(insurance);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get client by phone number
     */
    public Optional<ClientDTO> findByPhone(String phone) {
        return clientRepository.findByPhone(phone)
            .flatMap(Client::toClientDTO);
    }

    /**
     * Get clients by condition
     */
    public List<ClientDTO> findByCondition(String condition) {
        List<Client> clients = clientRepository.findByCondition(condition);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by location
     */
    public List<ClientDTO> findByLocation(String location) {
        List<Client> clients = clientRepository.findByLocation(location);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by name
     */
    public List<ClientDTO> findByName(String name) {
        List<Client> clients = clientRepository.findByName(name);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by date reachout
     */
    public List<ClientDTO> findByDateReachout(LocalDate dateReachout) {
        List<Client> clients = clientRepository.findByDateReachout(dateReachout);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get clients by date wanted
     */
    public List<ClientDTO> findByDateWanted(LocalDate dateWanted) {
        List<Client> clients = clientRepository.findByDateWanted(dateWanted);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search clients by name containing substring (case insensitive)
     */
    public List<ClientDTO> findByNameContaining(String name) {
        List<Client> clients = clientRepository.findByNameContainingIgnoreCase(name);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search clients by location containing substring (case insensitive)
     */
    public List<ClientDTO> findByLocationContaining(String location) {
        List<Client> clients = clientRepository.findByLocationContainingIgnoreCase(location);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search clients by condition containing substring (case insensitive)
     */
    public List<ClientDTO> findByConditionContaining(String condition) {
        List<Client> clients = clientRepository.findByConditionContainingIgnoreCase(condition);
        return clients.stream()
            .map(client -> client.toClientDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get active clients only
     */
    public List<ClientDTO> getActiveClients() {
        return findByStatus("Active");
    }

    /**
     * Get pending clients only
     */
    public List<ClientDTO> getPendingClients() {
        return findByStatus("Pending");
    }

    /**
     * Get inactive clients only
     */
    public List<ClientDTO> getInactiveClients() {
        return findByStatus("Inactive");
    }

    /**
     * Get living clients only (not deceased)
     */
    public List<ClientDTO> getLivingClients() {
        return findByDeceased(false);
    }
}