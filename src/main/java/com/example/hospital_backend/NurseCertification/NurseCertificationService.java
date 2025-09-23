package com.example.hospital_backend.NurseCertification;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseCertificationService {

    @Autowired
    NurseCertificationRepository NurseCertificationRepository;

    public Optional<NurseCertification> getNurseCertificationById(Long id) {
        return NurseCertificationRepository.findById(id);
    }

    public List<NurseCertification> getAllNurseCertifications() {
        return NurseCertificationRepository.findAll();
    }

    public NurseCertification saveNurseCertification(NurseCertification NurseCertification) {
        return NurseCertificationRepository.save(NurseCertification);
    }

    public void deleteNurseCertification(Long id) {
        NurseCertificationRepository.deleteById(id);
    }

}
