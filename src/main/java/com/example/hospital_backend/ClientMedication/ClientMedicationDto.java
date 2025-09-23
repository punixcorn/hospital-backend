// ClientMedicationDto.java
package com.example.hospital_backend.ClientMedication;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientMedicationDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long clientId;
    private String clientName;
    private Long medicationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String instructions;
    private String prescribedBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
}