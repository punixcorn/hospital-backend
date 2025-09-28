package com.example.hospital_backend.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Task management
 * 
 * Provides endpoints for:
 * - Creating, reading, updating, and deleting tasks
 * - Managing predefined task templates
 * - Assigning predefined tasks to nurses
 * - Searching and filtering tasks
 * 
 * Access: ADMIN and NURSE roles
 * - Nurses can create their own tasks
 * - Admins can assign predefined tasks to nurses
 * - Admins can manage predefined task templates
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ==================== TASK MANAGEMENT ENDPOINTS ====================

    /**
     * Get all tasks
     * 
     * @return List of all tasks with their basic information
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get task by ID
     * 
     * @param id Task ID (must be positive)
     * @return Task details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
            .map(task -> ResponseEntity.ok(task))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new task (by nurses)
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
     * 
     * @param request CreateTaskRequest with task details
     * @return Created task or error message
     */
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) {
        try {
            TaskDTO createdTask = taskService.createTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create task: " + e.getMessage()));
        }
    }

    /**
     * Update an existing task
     * 
     * @param id Task ID (must be positive)
     * @param request UpdateTaskRequest with fields to update
     * @return Updated task or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id, 
            @RequestBody UpdateTaskRequest request) {
        try {
            TaskDTO updatedTask = taskService.updateTask(id, request);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to update task: " + e.getMessage()));
        }
    }

    /**
     * Delete a task
     * 
     * @param id Task ID (must be positive)
     * @return 204 No Content on success, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete task: " + e.getMessage()));
        }
    }

    // ==================== TASK SEARCH & FILTER ENDPOINTS ====================

    /**
     * Get tasks by nurse ID
     * 
     * @param nurseId Nurse ID
     * @return List of tasks assigned to the specified nurse
     */
    @GetMapping("/by-nurse/{nurseId}")
    public ResponseEntity<List<TaskDTO>> getTasksByNurseId(@PathVariable Long nurseId) {
        List<TaskDTO> tasks = taskService.getTasksByNurseId(nurseId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by status
     * 
     * @param status Task status (Pending, In Progress, Completed, etc.)
     * @return List of tasks with the specified status
     */
    @GetMapping("/by-status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@RequestParam String status) {
        List<TaskDTO> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by completion status
     * 
     * @param completed true for completed tasks, false for incomplete tasks
     * @return List of tasks with the specified completion status
     */
    @GetMapping("/by-completion")
    public ResponseEntity<List<TaskDTO>> getTasksByCompletion(@RequestParam Boolean completed) {
        List<TaskDTO> tasks = taskService.getTasksByCompletion(completed);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by priority
     * 
     * @param priority Task priority (Low, Medium, High, Urgent)
     * @return List of tasks with the specified priority
     */
    @GetMapping("/by-priority")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(@RequestParam String priority) {
        List<TaskDTO> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by category
     * 
     * @param category Task category (Patient Care, Documentation, etc.)
     * @return List of tasks with the specified category
     */
    @GetMapping("/by-category")
    public ResponseEntity<List<TaskDTO>> getTasksByCategory(@RequestParam String category) {
        List<TaskDTO> tasks = taskService.getTasksByCategory(category);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get overdue tasks
     * 
     * @return List of tasks that are overdue (due date is before today and not completed)
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        List<TaskDTO> tasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks due today
     * 
     * @return List of tasks due today
     */
    @GetMapping("/due-today")
    public ResponseEntity<List<TaskDTO>> getTasksDueToday() {
        List<TaskDTO> tasks = taskService.getTasksDueToday();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks due between two dates
     * 
     * @param startDate Start date (inclusive)
     * @param endDate End date (inclusive)
     * @return List of tasks due between the specified dates
     */
    @GetMapping("/due-between")
    public ResponseEntity<List<TaskDTO>> getTasksDueBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TaskDTO> tasks = taskService.getTasksDueBetween(startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Search tasks by title containing substring (case insensitive)
     * 
     * @param title Title substring to search for
     * @return List of tasks containing the specified title substring
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<TaskDTO>> searchTasksByTitle(@RequestParam String title) {
        List<TaskDTO> tasks = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Search tasks by description containing substring (case insensitive)
     * 
     * @param description Description substring to search for
     * @return List of tasks containing the specified description substring
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<TaskDTO>> searchTasksByDescription(@RequestParam String description) {
        List<TaskDTO> tasks = taskService.searchTasksByDescription(description);
        return ResponseEntity.ok(tasks);
    }

    // ==================== PREDEFINED TASK MANAGEMENT ENDPOINTS (ADMIN ONLY) ====================

    /**
     * Get all predefined task templates
     * 
     * @return List of all predefined task templates
     */
    @GetMapping("/predefined")
    public ResponseEntity<List<PredefinedTask>> getAllPredefinedTasks() {
        List<PredefinedTask> predefinedTasks = taskService.getAllPredefinedTasks();
        return ResponseEntity.ok(predefinedTasks);
    }

    /**
     * Get active predefined task templates
     * 
     * @return List of active predefined task templates
     */
    @GetMapping("/predefined/active")
    public ResponseEntity<List<PredefinedTask>> getActivePredefinedTasks() {
        List<PredefinedTask> predefinedTasks = taskService.getActivePredefinedTasks();
        return ResponseEntity.ok(predefinedTasks);
    }

    /**
     * Get predefined tasks by category
     * 
     * @param category Task category
     * @return List of active predefined tasks in the specified category
     */
    @GetMapping("/predefined/by-category")
    public ResponseEntity<List<PredefinedTask>> getPredefinedTasksByCategory(@RequestParam String category) {
        List<PredefinedTask> predefinedTasks = taskService.getPredefinedTasksByCategory(category);
        return ResponseEntity.ok(predefinedTasks);
    }

    /**
     * Create a new predefined task template (ADMIN ONLY)
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
     * 
     * @param request CreatePredefinedTaskRequest with predefined task details
     * @return Created predefined task or error message
     */
    @PostMapping("/predefined")
    public ResponseEntity<?> createPredefinedTask(@RequestBody CreatePredefinedTaskRequest request) {
        try {
            PredefinedTask createdPredefinedTask = taskService.createPredefinedTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPredefinedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create predefined task: " + e.getMessage()));
        }
    }

    /**
     * Assign predefined task to multiple nurses (ADMIN ONLY)
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
     * 
     * @param request AssignTaskRequest with assignment details
     * @return List of created tasks or error message
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignPredefinedTask(@RequestBody AssignTaskRequest request) {
        try {
            List<TaskDTO> createdTasks = taskService.assignPredefinedTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to assign predefined task: " + e.getMessage()));
        }
    }

    // ==================== TASK COUNT ENDPOINTS ====================

    /**
     * Count tasks by nurse
     * 
     * @param nurseId Nurse ID
     * @return Count of tasks assigned to the nurse
     */
    @GetMapping("/count/nurse/{nurseId}")
    public ResponseEntity<Map<String, Object>> countTasksByNurse(@PathVariable Long nurseId) {
        long count = taskService.countTasksByNurse(nurseId);
        return ResponseEntity.ok(Map.of("nurseId", nurseId, "count", count));
    }

    /**
     * Count tasks by nurse and status
     * 
     * @param nurseId Nurse ID
     * @param status Task status
     * @return Count of tasks assigned to the nurse with the specified status
     */
    @GetMapping("/count/nurse/{nurseId}/status")
    public ResponseEntity<Map<String, Object>> countTasksByNurseAndStatus(
            @PathVariable Long nurseId, 
            @RequestParam String status) {
        long count = taskService.countTasksByNurseAndStatus(nurseId, status);
        return ResponseEntity.ok(Map.of("nurseId", nurseId, "status", status, "count", count));
    }

    /**
     * Count overdue tasks for a nurse
     * 
     * @param nurseId Nurse ID
     * @return Count of overdue tasks for the nurse
     */
    @GetMapping("/count/nurse/{nurseId}/overdue")
    public ResponseEntity<Map<String, Object>> countOverdueTasksByNurse(@PathVariable Long nurseId) {
        long count = taskService.countOverdueTasksByNurse(nurseId);
        return ResponseEntity.ok(Map.of("nurseId", nurseId, "overdueCount", count));
    }
}
