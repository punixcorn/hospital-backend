// ClientDTO.java
package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.hospital_backend.AssessmentInfo.AssessmentInfoDTO;
import com.example.hospital_backend.Driver.DriverDto;
import com.example.hospital_backend.Item.ItemDto;
import com.example.hospital_backend.Medication.MedicationDTO;
import com.example.hospital_backend.Nurse.NurseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
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
    private List<ItemDto> items;
    private List<NurseDTO> assignedNurses;
    private List<DriverDto> assignedDrivers;

    private Long assessmentInfoId;
    private AssessmentInfoDTO assessmentInfo;
}