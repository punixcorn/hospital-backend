package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Request DTO for creating a new client
 * 
 * Required fields:
 * - name: Client's full name (max 255 characters)
 * - status: Client status (Active, Pending, Inactive, etc.)
 * - phone: Unique phone number (max 20 characters)
 * 
 * Optional fields:
 * - location: Client's address/location (max 500 characters)
 * - coordinatesLat/coordinatesLng: GPS coordinates
 * - message: Additional message/notes
 * - description: Detailed description of client needs
 * - dateReachout: Date when client first reached out
 * - dateWanted: Date when client wants service to start
 * - serviceType: Type of service needed (Home Care, Rehabilitation, etc.)
 * - age: Client's age
 * - height: Client's height
 * - deceased: Whether client is deceased (defaults to false)
 * - condition: Medical conditions
 * - insurance: Insurance provider
 * - emergencyContact: Emergency contact information
 * - medicationIds: List of medication IDs
 */
@Data
public class CreateClientRequest {
    
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
    
    private Boolean deceased = false;
    
    private String condition;
    
    private String insurance;
    
    private String emergencyContact;
    
    private List<Long> medicationIds;
}
