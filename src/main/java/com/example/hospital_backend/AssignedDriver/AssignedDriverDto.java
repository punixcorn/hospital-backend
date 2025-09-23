// AssignedDriverDto.java
package com.example.hospital_backend.AssignedDriver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedDriverDto {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long driverId;
    private String driverName;
    private String status;
}

