package com.example.hospital_backend.Note;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Note
 * 
 * Represents a note with its essential information for API responses.
 * Includes references to nurse and client IDs instead of full entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long nurseId;
    private Long clientId;
    private String clientName;
    private LocalDateTime timestamp;
    private String content;
}
