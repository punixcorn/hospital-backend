package com.example.hospital_backend.Driver;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    DriverRepository DriverRepository;

    public Optional<Driver> getDriverById(Long id) {
        return DriverRepository.findById(id);
    }

    public List<Driver> getAllDrivers() {
        return DriverRepository.findAll();
    }

    public Driver saveDriver(Driver Driver) {
        return DriverRepository.save(Driver);
    }

    public void deleteDriver(Long id) {
        DriverRepository.deleteById(id);
    }

}
