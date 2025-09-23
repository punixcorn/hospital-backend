package com.example.hospital_backend.PreviousClient;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreviousClientService {

    @Autowired
    PreviousClientRepository PreviousClientRepository;

    public Optional<PreviousClient> getPreviousClientById(Long id) {
        return PreviousClientRepository.findById(id);
    }

    public List<PreviousClient> getAllPreviousClients() {
        return PreviousClientRepository.findAll();
    }

    public PreviousClient savePreviousClient(PreviousClient PreviousClient) {
        return PreviousClientRepository.save(PreviousClient);
    }

    public void deletePreviousClient(Long id) {
        PreviousClientRepository.deleteById(id);
    }

}
