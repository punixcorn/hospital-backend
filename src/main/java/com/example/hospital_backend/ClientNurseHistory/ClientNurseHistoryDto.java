
// ClientNurseHistoryDto.java
package com.example.hospital_backend.ClientNurseHistory;

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
public class ClientNurseHistoryDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long clientId;
    private String clientName;
    private Long nurseId;
    private String nurseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer hoursWorked;
}
