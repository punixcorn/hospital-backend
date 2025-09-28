package com.example.hospital_backend.Task;

import java.time.LocalDate;

import lombok.Data;

/**
 * Request DTO for updating an existing task
 * 
 * All fields are optional for partial updates.
 * Validation rules apply when fields are provided.
 */
@Data
public class UpdateTaskRequest {
    
    private String title;
    
    private String status;
    
    private String description;
    
    private LocalDate dueDate;
    
    private Boolean completed;
    
    private String priority;
    
    private String category;
}
