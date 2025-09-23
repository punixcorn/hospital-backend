// AssignedClientDto.java (Helper DTO for Driver's assigned clients)
package com.example.hospital_backend.Driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedClientDto {
    private Long id;
    private String name;
    private String email;
    private String status;
}