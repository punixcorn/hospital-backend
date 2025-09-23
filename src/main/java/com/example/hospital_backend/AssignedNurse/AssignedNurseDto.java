// AssignedNurseDto.java
package com.example.hospital_backend.AssignedNurse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedNurseDto {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long nurseId;
    private String nurseName;
    private Integer hours;
}