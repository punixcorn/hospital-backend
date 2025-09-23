package com.example.hospital_backend.ClientNurseHistory;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientNurseHistoryService {

    @Autowired
    ClientNurseHistoryRepository ClientNurseHistoryRepository;

    public Optional<ClientNurseHistory> getClientNurseHistoryById(Long id) {
        return ClientNurseHistoryRepository.findById(id);
    }

    public List<ClientNurseHistory> getAllClientNurseHistorys() {
        return ClientNurseHistoryRepository.findAll();
    }

    public ClientNurseHistory saveClientNurseHistory(ClientNurseHistory ClientNurseHistory) {
        return ClientNurseHistoryRepository.save(ClientNurseHistory);
    }

    public void deleteClientNurseHistory(Long id) {
        ClientNurseHistoryRepository.deleteById(id);
    }

}
