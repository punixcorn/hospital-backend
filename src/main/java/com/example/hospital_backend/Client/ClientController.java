package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping()
    public ResponseEntity<List<Client>> findAll() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/status")
    public ResponseEntity<List<Client>> findByStatus(@RequestParam String status) {
        List<Client> clients = clientService.findByStatus(status);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/serviceType")
    public ResponseEntity<List<Client>> findByServiceType(@RequestParam String serviceType) {
        List<Client> clients = clientService.findByServiceType(serviceType);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/age")
    public ResponseEntity<List<Client>> findByAge(@RequestParam Integer age) {
        List<Client> clients = clientService.findByAge(age);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/deceased")
    public ResponseEntity<List<Client>> findByDeceased(@RequestParam Boolean deceased) {
        List<Client> clients = clientService.findByDeceased(deceased);
        return ResponseEntity.ok(clients);
    }

    // @GetMapping(path = "/items")
    // public ResponseEntity<List<Client>> findByItemsContaining(@RequestParam String itemName) {
    //     List<Client> clients = clientService.findByItemsContaining(itemName);
    //     return ResponseEntity.ok(clients);
    // }

    @GetMapping(path = "/assignedNurses")
    public ResponseEntity<List<Client>> findByAssignedNurseIds(@RequestParam List<Long> nurseIds) {
        List<Client> clients = clientService.findByAssignedNurseIds(nurseIds);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/medications")
    public ResponseEntity<List<Client>> findByMedicationIds(@RequestParam List<Long> medicationIds) {
        List<Client> clients = clientService.findByMedicationIds(medicationIds);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/insurance")
    public ResponseEntity<List<Client>> findByInsurance(@RequestParam String insurance) {
        List<Client> clients = clientService.findByInsurance(insurance);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/phone")
    public ResponseEntity<List<Client>> findByPhone(@RequestParam String phone) {
        List<Client> clients = clientService.findByPhone(phone);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/condition")
    public ResponseEntity<List<Client>> findByCondition(@RequestParam String condition) {
        List<Client> clients = clientService.findByCondition(condition);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/location")
    public ResponseEntity<List<Client>> findByLocation(@RequestParam String location) {
        List<Client> clients = clientService.findByLocation(location);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/name")
    public ResponseEntity<List<Client>> findByName(@RequestParam String name) {
        List<Client> clients = clientService.findByName(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/dateReachout")
    public ResponseEntity<List<Client>> findByDateReachout(@RequestParam LocalDate dateReachout) {
        List<Client> clients = clientService.findByDateReachout(dateReachout);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/dateWanted")
    public ResponseEntity<List<Client>> findByDateWanted(@RequestParam LocalDate dateWanted) {
        List<Client> clients = clientService.findByDateWanted(dateWanted);
        return ResponseEntity.ok(clients);
    }

    // Additional search endpoints for partial matching
    @GetMapping(path = "/name/search")
    public ResponseEntity<List<Client>> findByNameContaining(@RequestParam String name) {
        List<Client> clients = clientService.findByNameContaining(name);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/location/search")
    public ResponseEntity<List<Client>> findByLocationContaining(@RequestParam String location) {
        List<Client> clients = clientService.findByLocationContaining(location);
        return ResponseEntity.ok(clients);
    }

    @GetMapping(path = "/condition/search")
    public ResponseEntity<List<Client>> findByConditionContaining(@RequestParam String condition) {
        List<Client> clients = clientService.findByConditionContaining(condition);
        return ResponseEntity.ok(clients);
    }
}