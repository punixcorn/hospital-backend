package com.example.hospital_backend.Task;

import java.time.LocalDate;

import lombok.Data;

/**
 * Request DTO for creating a new task (by nurses)
 * 
 * Required fields:
 * - nurseId: ID of the nurse creating the task (must reference existing nurse)
 * - title: Task title (cannot be empty)
 * - status: Task status (Pending, In Progress, Completed, etc.)
 * 
 * Optional fields:
 * - description: Detailed task description
 * - dueDate: When the task should be completed
 * - priority: Task priority (Low, Medium, High, Urgent)
 * - category: Task category (Patient Care, Documentation, etc.)
 */
@Data
public class CreateTaskRequest {
    
    private Long nurseId;
    
    private String title;
    
    private String status;
    
    private String description;
    
    private LocalDate dueDate;
    
    private String priority;
    
    private String category;
}
