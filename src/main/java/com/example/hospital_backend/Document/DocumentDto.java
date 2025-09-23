// DocumentDto.java
package com.example.hospital_backend.Document;

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
public class DocumentDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long nurseId;
    private String nurseName;
    private String name;
    private LocalDate date;
    private String type;
    private String url;
}
