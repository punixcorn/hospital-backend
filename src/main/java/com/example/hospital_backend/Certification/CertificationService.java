package com.example.hospital_backend.Certification;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificationService {

    @Autowired
    CertificationRepository CertificationRepository;

    public Optional<Certification> getCertificationById(Long id) {
        return CertificationRepository.findById(id);
    }

    public List<Certification> getAllCertifications() {
        return CertificationRepository.findAll();
    }

    public Certification saveCertification(Certification Certification) {
        return CertificationRepository.save(Certification);
    }

    public void deleteCertification(Long id) {
        CertificationRepository.deleteById(id);
    }

}
