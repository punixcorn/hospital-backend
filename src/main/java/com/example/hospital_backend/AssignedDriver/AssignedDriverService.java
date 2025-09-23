package com.example.hospital_backend.AssignedDriver;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignedDriverService {

    @Autowired
    AssignedDriverRepository AssignedDriverRepository;

    public Optional<AssignedDriver> getAssignedDriverById(Long id) {
        return AssignedDriverRepository.findById(id);
    }

    public List<AssignedDriver> getAllAssignedDrivers() {
        return AssignedDriverRepository.findAll();
    }

    public AssignedDriver saveAssignedDriver(AssignedDriver AssignedDriver) {
        return AssignedDriverRepository.save(AssignedDriver);
    }

    public void deleteAssignedDriver(Long id) {
        AssignedDriverRepository.deleteById(id);
    }

}
