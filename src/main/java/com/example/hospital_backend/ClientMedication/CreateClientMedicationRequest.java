package com.example.hospital_backend.ClientMedication;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientMedicationRequest {
    private Long clientId;
    private Long medicationId;
    
    private String dosage;
    private String frequency;
    private String instructions;
    private String prescribedBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
}