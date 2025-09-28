package com.example.hospital_backend.Note;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Request DTO for creating a new note
 * 
 * Required fields:
 * - nurseId: ID of the nurse creating the note (must reference existing nurse)
 * - clientId: ID of the client the note is about (must reference existing client)
 * - content: The note content (cannot be empty)
 * 
 * Optional fields:
 * - timestamp: When the note was created (defaults to current time)
 */
@Data
public class CreateNoteRequest {
    
    private Long nurseId;
    
    private Long clientId;
    
    private LocalDateTime timestamp;
    
    private String content;
}
