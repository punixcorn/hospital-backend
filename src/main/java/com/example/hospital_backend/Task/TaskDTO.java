package com.example.hospital_backend.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Task
 * 
 * Represents a task with its essential information for API responses.
 * Includes references to nurse ID and name instead of full entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long nurseId;
    private String title;
    private String status;
    private LocalDate dueDate;
    private Boolean completed;
    private String description;
    private String priority;
    private String category;
}
