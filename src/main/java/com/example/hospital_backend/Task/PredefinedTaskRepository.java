package com.example.hospital_backend.Task;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredefinedTaskRepository extends JpaRepository<PredefinedTask, Long> {
    
    /**
     * Find predefined tasks by category
     */
    List<PredefinedTask> findByCategory(String category);
    
    /**
     * Find predefined tasks by priority
     */
    List<PredefinedTask> findByPriority(String priority);
    
    /**
     * Find active predefined tasks
     */
    List<PredefinedTask> findByIsActiveTrue();
    
    /**
     * Find inactive predefined tasks
     */
    List<PredefinedTask> findByIsActiveFalse();
    
    /**
     * Find predefined tasks by category and active status
     */
    List<PredefinedTask> findByCategoryAndIsActive(String category, Boolean isActive);
    
    /**
     * Find predefined tasks by priority and active status
     */
    List<PredefinedTask> findByPriorityAndIsActive(String priority, Boolean isActive);
    
    /**
     * Find predefined tasks by title containing substring (case insensitive)
     */
    List<PredefinedTask> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find predefined tasks by description containing substring (case insensitive)
     */
    List<PredefinedTask> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * Count active predefined tasks
     */
    long countByIsActiveTrue();
    
    /**
     * Count predefined tasks by category
     */
    long countByCategory(String category);
    
    /**
     * Count predefined tasks by priority
     */
    long countByPriority(String priority);
}
