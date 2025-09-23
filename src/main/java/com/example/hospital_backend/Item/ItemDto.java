// ItemDto.java
package com.example.hospital_backend.Item;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String id;
    private Long clientId;
    private String clientName;
    private String name;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}