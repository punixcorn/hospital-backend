package com.example.hospital_backend.Task;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for predefined task templates
 * 
 * These are template tasks that can be used to create actual tasks for nurses.
 * Admins can create predefined tasks, and then use them to assign tasks to nurses.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredefinedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String priority;

    @Column(length = 50, nullable = false)
    private String defaultStatus;

    @Column(nullable = false)
    private Integer defaultDueDays;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
