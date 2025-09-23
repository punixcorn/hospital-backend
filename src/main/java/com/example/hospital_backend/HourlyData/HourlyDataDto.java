package com.example.hospital_backend.HourlyData;
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
public class HourlyDataDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate date;
    private Integer hour;
    private Integer worked;
    private Double earnings;
    private Long nurseId;
    private String nurseName;
}