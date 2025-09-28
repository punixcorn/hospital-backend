package com.example.hospital_backend.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Find tasks by nurse ID
     */
    List<Task> findByNurseId(Long nurseId);
    
    /**
     * Find tasks by status
     */
    List<Task> findByStatus(String status);
    
    /**
     * Find tasks by completion status
     */
    List<Task> findByCompleted(Boolean completed);
    
    /**
     * Find tasks by priority
     */
    List<Task> findByPriority(String priority);
    
    /**
     * Find tasks by category
     */
    List<Task> findByCategory(String category);
    
    /**
     * Find tasks by due date
     */
    List<Task> findByDueDate(LocalDate dueDate);
    
    /**
     * Find tasks due before a specific date
     */
    List<Task> findByDueDateBefore(LocalDate dueDate);
    
    /**
     * Find tasks due after a specific date
     */
    List<Task> findByDueDateAfter(LocalDate dueDate);
    
    /**
     * Find tasks due between two dates
     */
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Find overdue tasks (due date is before today and not completed)
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate < :today AND t.completed = false")
    List<Task> findOverdueTasks(@Param("today") LocalDate today);
    
    /**
     * Find tasks due today
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate = :today")
    List<Task> findTasksDueToday(@Param("today") LocalDate today);
    
    /**
     * Find tasks by nurse and status
     */
    List<Task> findByNurseIdAndStatus(Long nurseId, String status);
    
    /**
     * Find tasks by nurse and completion status
     */
    List<Task> findByNurseIdAndCompleted(Long nurseId, Boolean completed);
    
    /**
     * Find tasks by nurse and priority
     */
    List<Task> findByNurseIdAndPriority(Long nurseId, String priority);
    
    /**
     * Find tasks by nurse and category
     */
    List<Task> findByNurseIdAndCategory(Long nurseId, String category);
    
    /**
     * Find tasks by title containing substring (case insensitive)
     */
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find tasks by description containing substring (case insensitive)
     */
    List<Task> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * Count tasks by nurse
     */
    long countByNurseId(Long nurseId);
    
    /**
     * Count tasks by nurse and status
     */
    long countByNurseIdAndStatus(Long nurseId, String status);
    
    /**
     * Count tasks by nurse and completion status
     */
    long countByNurseIdAndCompleted(Long nurseId, Boolean completed);
    
    /**
     * Count overdue tasks for a nurse
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.nurse.id = :nurseId AND t.dueDate < :today AND t.completed = false")
    long countOverdueTasksByNurse(@Param("nurseId") Long nurseId, @Param("today") LocalDate today);
}
