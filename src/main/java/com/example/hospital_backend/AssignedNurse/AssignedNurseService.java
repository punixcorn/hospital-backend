package com.example.hospital_backend.AssignedNurse;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignedNurseService {

    @Autowired
    AssignedNurseRepository AssignedNurseRepository;

    public Optional<AssignedNurse> getAssignedNurseById(Long id) {
        return AssignedNurseRepository.findById(id);
    }

    public List<AssignedNurse> getAllAssignedNurses() {
        return AssignedNurseRepository.findAll();
    }

    public AssignedNurse saveAssignedNurse(AssignedNurse AssignedNurse) {
        return AssignedNurseRepository.save(AssignedNurse);
    }

    public void deleteAssignedNurse(Long id) {
        AssignedNurseRepository.deleteById(id);
    }

}
