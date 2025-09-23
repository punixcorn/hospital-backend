// CreateAssignedDriverRequest.java
package com.example.hospital_backend.AssignedDriver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssignedDriverRequest {
    private Long clientId;
    private Long driverId;
    private String status;
}
