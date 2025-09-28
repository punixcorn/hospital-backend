package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Request DTO for updating an existing client
 * 
 * All fields are optional for partial updates.
 * Validation rules apply when fields are provided.
 */
@Data
public class UpdateClientRequest {
    
    private String name;
    
    private String status;
    
    private String location;
    
    private Double coordinatesLat;
    
    private Double coordinatesLng;
    
    private String message;
    
    private String description;
    
    private LocalDate dateReachout;
    
    private LocalDate dateWanted;
    
    private String serviceType;
    
    private Integer age;
    
    private String height;
    
    private String phone;
    
    private Boolean deceased;
    
    private String condition;
    
    private String insurance;
    
    private String emergencyContact;
    
    private List<Long> medication_ids;

    private List<Long> item_ids;
}
