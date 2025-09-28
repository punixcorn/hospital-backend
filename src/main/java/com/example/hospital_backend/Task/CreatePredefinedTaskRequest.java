package com.example.hospital_backend.Task;

import lombok.Data;

/**
 * Request DTO for creating a new predefined task template (by admins)
 * 
 * Required fields:
 * - title: Task title (cannot be empty)
 * - category: Task category (Patient Care, Documentation, etc.)
 * - priority: Task priority (Low, Medium, High, Urgent)
 * - defaultStatus: Default status for created tasks
 * - defaultDueDays: Default number of days until due date
 * 
 * Optional fields:
 * - description: Detailed task description
 */
@Data
public class CreatePredefinedTaskRequest {
    
    private String title;
    
    private String description;
    
    private String category;
    
    private String priority;
    
    private String defaultStatus;
    
    private Integer defaultDueDays;
}
