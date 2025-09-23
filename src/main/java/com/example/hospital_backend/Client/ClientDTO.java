// ClientDTO.java
package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.hospital_backend.AssessmentInfo.AssessmentInfoDTO;
import com.example.hospital_backend.Medication.MedicationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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
    private String clientLinkID;
    private Integer age;
    private String height;
    private String phone;
    private Boolean deceased;
    private String condition;
    private String insurance;
    private String emergencyContact;
    private List<MedicationDTO> medications;
    private AssessmentInfoDTO assessmentInfo;
}