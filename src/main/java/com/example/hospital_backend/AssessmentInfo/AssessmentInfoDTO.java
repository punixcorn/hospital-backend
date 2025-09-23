package com.example.hospital_backend.AssessmentInfo;

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
public class AssessmentInfoDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long nurseId;
    private String nurseName;
    private Long clientId;
    private String clientName;
    private LocalDate assessmentDate;
    private String mobility;
    private String medications;
    private String specialNeeds;
    private String notes;
    private String status;
    private Long createdBy;
}
