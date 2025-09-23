package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public List<Client> findByStatus(String status) {
        return clientRepository.findByStatus(status);
    }

    public List<Client> findByServiceType(String serviceType) {
        return clientRepository.findByServiceType(serviceType);
    }

    public List<Client> findByAge(Integer age) {
        return clientRepository.findByAge(age);
    }

    public List<Client> findByDeceased(Boolean deceased) {
        return clientRepository.findByDeceased(deceased);
    }

    // public List<Client> findByItemsContaining(String itemName) {
    //     return clientRepository.findByItemsContaining(itemName);
    // }

    public List<Client> findByAssignedNurseIds(List<Long> nurseIds) {
        return clientRepository.findByAssignedNurseIds(nurseIds);
    }

    public List<Client> findByMedicationIds(List<Long> medicationIds) {
        return clientRepository.findByMedicationIds(medicationIds);
    }

    public List<Client> findByInsurance(String insurance) {
        return clientRepository.findByInsurance(insurance);
    }

    public List<Client> findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    public List<Client> findByCondition(String condition) {
        return clientRepository.findByCondition(condition);
    }

    public List<Client> findByLocation(String location) {
        return clientRepository.findByLocation(location);
    }

    public List<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    public List<Client> findByDateReachout(LocalDate dateReachout) {
        return clientRepository.findByDateReachout(dateReachout);
    }

    public List<Client> findByDateWanted(LocalDate dateWanted) {
        return clientRepository.findByDateWanted(dateWanted);
    }

    // Additional search methods
    public List<Client> findByNameContaining(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Client> findByLocationContaining(String location) {
        return clientRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Client> findByConditionContaining(String condition) {
        return clientRepository.findByConditionContainingIgnoreCase(condition);
    }
}