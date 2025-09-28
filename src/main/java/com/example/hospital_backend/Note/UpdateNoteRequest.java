package com.example.hospital_backend.Note;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Request DTO for updating an existing note
 * 
 * All fields are optional for partial updates.
 * Validation rules apply when fields are provided.
 */
@Data
public class UpdateNoteRequest {
    
    private LocalDateTime timestamp;
    
    private String content;
}
