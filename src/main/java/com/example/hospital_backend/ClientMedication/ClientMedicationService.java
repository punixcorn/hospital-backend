package com.example.hospital_backend.ClientMedication;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientMedicationService {

    @Autowired
    ClientMedicationRepository ClientMedicationRepository;

    public Optional<ClientMedication> getClientMedicationById(Long id) {
        return ClientMedicationRepository.findById(id);
    }

    public List<ClientMedication> getAllClientMedications() {
        return ClientMedicationRepository.findAll();
    }

    public ClientMedication saveClientMedication(ClientMedication ClientMedication) {
        return ClientMedicationRepository.save(ClientMedication);
    }

    public void deleteClientMedication(Long id) {
        ClientMedicationRepository.deleteById(id);
    }

}
