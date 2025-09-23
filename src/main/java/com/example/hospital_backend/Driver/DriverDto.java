// DriverDto.java
package com.example.hospital_backend.Driver;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private String userEmail;
    private String status;
    private Integer age;
    private String carName;
    private String carNumber;
    private String currentClient;
    private Long currentNurseId;
    private String currentNurseName;
    private Integer hoursWorked;
    private Integer totalHoursAssigned;
    private List<AssignedClientDto> assignedClients;
}
