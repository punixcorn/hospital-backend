package com.example.hospital_backend.Task;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Request DTO for assigning tasks to nurses (by admins)
 * 
 * Required fields:
 * - nurseIds: List of nurse IDs to assign tasks to
 * - predefinedTaskId: ID of the predefined task template to use
 * 
 * Optional fields:
 * - dueDate: Override the default due date from predefined task
 * - priority: Override the default priority from predefined task
 * - status: Override the default status from predefined task
 * - description: Additional description to append to predefined task description
 */
@Data
public class AssignTaskRequest {
    
    private List<Long> nurseIds;
    
    private Long predefinedTaskId;
    
    private LocalDate dueDate;
    
    private String priority;
    
    private String status;
    
    private String description;
}
