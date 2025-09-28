package com.example.hospital_backend.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hospital_backend.Nurse.Nurse;
import com.example.hospital_backend.Nurse.NurseRepository;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private PredefinedTaskRepository predefinedTaskRepository;
    
    @Autowired
    private NurseRepository nurseRepository;

    /**
     * Get all tasks with their basic information
     */
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get task by ID with full details
     */
    public Optional<TaskDTO> getTaskById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return taskRepository.findById(id)
            .flatMap(Task::toTaskDTO);
    }

    /**
     * Create a new task (by nurses)
     */
    public TaskDTO createTask(CreateTaskRequest request) {
        // Validate required fields
        if (request.getNurseId() == null || request.getNurseId() <= 0) {
            throw new IllegalArgumentException("Valid nurse ID is required");
        }
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title is required");
        }
        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Task status is required");
        }
        
        // Validate nurse exists
        Nurse nurse = nurseRepository.findById(request.getNurseId())
            .orElseThrow(() -> new IllegalArgumentException("Nurse not found with ID: " + request.getNurseId()));
        
        // Build task entity
        Task task = Task.builder()
            .nurseId(nurse.getId())
            .title(request.getTitle().trim())
            .status(request.getStatus().trim())
            .description(request.getDescription() != null ? request.getDescription().trim() : null)
            .dueDate(request.getDueDate())
            .priority(request.getPriority())
            .category(request.getCategory())
            .completed(false)
            .build();
        
        Task savedTask = taskRepository.save(task);
        return savedTask.toTaskDTO().orElseThrow(() -> new RuntimeException("Failed to convert task to DTO"));
    }

    /**
     * Update an existing task
     */
    public TaskDTO updateTask(Long id, UpdateTaskRequest request) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
        
        // Update fields if provided
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            existingTask.setTitle(request.getTitle().trim());
        } else if (request.getTitle() != null && request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            existingTask.setStatus(request.getStatus().trim());
        }
        
        if (request.getDescription() != null) {
            existingTask.setDescription(request.getDescription().trim());
        }
        
        if (request.getDueDate() != null) {
            existingTask.setDueDate(request.getDueDate());
        }
        
        if (request.getCompleted() != null) {
            existingTask.setCompleted(request.getCompleted());
        }
        
        if (request.getPriority() != null) {
            existingTask.setPriority(request.getPriority());
        }
        
        if (request.getCategory() != null) {
            existingTask.setCategory(request.getCategory());
        }
        
        Task updatedTask = taskRepository.save(existingTask);
        return updatedTask.toTaskDTO().orElseThrow(() -> new RuntimeException("Failed to convert task to DTO"));
    }

    /**
     * Delete a task
     */
    public void deleteTask(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid task ID");
        }
        
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }
        
        taskRepository.deleteById(id);
    }

    /**
     * Assign predefined task to multiple nurses (by admins)
     */
    public List<TaskDTO> assignPredefinedTask(AssignTaskRequest request) {
        // Validate required fields
        if (request.getNurseIds() == null || request.getNurseIds().isEmpty()) {
            throw new IllegalArgumentException("At least one nurse ID is required");
        }
        if (request.getPredefinedTaskId() == null || request.getPredefinedTaskId() <= 0) {
            throw new IllegalArgumentException("Valid predefined task ID is required");
        }
        
        // Validate predefined task exists
        PredefinedTask predefinedTask = predefinedTaskRepository.findById(request.getPredefinedTaskId())
            .orElseThrow(() -> new IllegalArgumentException("Predefined task not found with ID: " + request.getPredefinedTaskId()));
        
        if (!predefinedTask.getIsActive()) {
            throw new IllegalArgumentException("Cannot assign inactive predefined task");
        }
        
        // Validate all nurses exist
        List<Nurse> nurses = nurseRepository.findAllById(request.getNurseIds());
        if (nurses.size() != request.getNurseIds().size()) {
            throw new IllegalArgumentException("One or more nurses not found");
        }
        
        // Calculate due date
        LocalDate dueDate = request.getDueDate() != null ? 
            request.getDueDate() : 
            LocalDate.now().plusDays(predefinedTask.getDefaultDueDays());
        
        // Create tasks for each nurse
        List<Task> createdTasks = request.getNurseIds().stream()
            .map(nurseId -> Task.builder()
                .nurseId(nurseId)
                .title(predefinedTask.getTitle())
                .description(buildDescription(predefinedTask.getDescription(), request.getDescription()))
                .status(request.getStatus() != null ? request.getStatus() : predefinedTask.getDefaultStatus())
                .dueDate(dueDate)
                .priority(request.getPriority() != null ? request.getPriority() : predefinedTask.getPriority())
                .category(predefinedTask.getCategory())
                .completed(false)
                .build())
            .map(taskRepository::save)
            .toList();
        
        return createdTasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Create a new predefined task template (by admins)
     */
    public PredefinedTask createPredefinedTask(CreatePredefinedTaskRequest request) {
        // Validate required fields
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Predefined task title is required");
        }
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (request.getPriority() == null || request.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority is required");
        }
        if (request.getDefaultStatus() == null || request.getDefaultStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Default status is required");
        }
        if (request.getDefaultDueDays() == null || request.getDefaultDueDays() <= 0) {
            throw new IllegalArgumentException("Default due days must be positive");
        }
        
        // Build predefined task entity
        PredefinedTask predefinedTask = PredefinedTask.builder()
            .title(request.getTitle().trim())
            .description(request.getDescription() != null ? request.getDescription().trim() : null)
            .category(request.getCategory().trim())
            .priority(request.getPriority().trim())
            .defaultStatus(request.getDefaultStatus().trim())
            .defaultDueDays(request.getDefaultDueDays())
            .isActive(true)
            .build();
        
        return predefinedTaskRepository.save(predefinedTask);
    }

    /**
     * Get tasks by nurse ID
     */
    public List<TaskDTO> getTasksByNurseId(Long nurseId) {
        List<Task> tasks = taskRepository.findByNurseId(nurseId);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks by status
     */
    public List<TaskDTO> getTasksByStatus(String status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks by completion status
     */
    public List<TaskDTO> getTasksByCompletion(Boolean completed) {
        List<Task> tasks = taskRepository.findByCompleted(completed);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks by priority
     */
    public List<TaskDTO> getTasksByPriority(String priority) {
        List<Task> tasks = taskRepository.findByPriority(priority);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks by category
     */
    public List<TaskDTO> getTasksByCategory(String category) {
        List<Task> tasks = taskRepository.findByCategory(category);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get overdue tasks
     */
    public List<TaskDTO> getOverdueTasks() {
        List<Task> tasks = taskRepository.findOverdueTasks(LocalDate.now());
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks due today
     */
    public List<TaskDTO> getTasksDueToday() {
        List<Task> tasks = taskRepository.findTasksDueToday(LocalDate.now());
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get tasks due between two dates
     */
    public List<TaskDTO> getTasksDueBetween(LocalDate startDate, LocalDate endDate) {
        List<Task> tasks = taskRepository.findByDueDateBetween(startDate, endDate);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search tasks by title containing substring
     */
    public List<TaskDTO> searchTasksByTitle(String title) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search tasks by description containing substring
     */
    public List<TaskDTO> searchTasksByDescription(String description) {
        List<Task> tasks = taskRepository.findByDescriptionContainingIgnoreCase(description);
        return tasks.stream()
            .map(task -> task.toTaskDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get all predefined tasks
     */
    public List<PredefinedTask> getAllPredefinedTasks() {
        return predefinedTaskRepository.findAll();
    }

    /**
     * Get active predefined tasks
     */
    public List<PredefinedTask> getActivePredefinedTasks() {
        return predefinedTaskRepository.findByIsActiveTrue();
    }

    /**
     * Get predefined tasks by category
     */
    public List<PredefinedTask> getPredefinedTasksByCategory(String category) {
        return predefinedTaskRepository.findByCategoryAndIsActive(category, true);
    }

    /**
     * Count tasks by nurse
     */
    public long countTasksByNurse(Long nurseId) {
        return taskRepository.countByNurseId(nurseId);
    }

    /**
     * Count tasks by nurse and status
     */
    public long countTasksByNurseAndStatus(Long nurseId, String status) {
        return taskRepository.countByNurseIdAndStatus(nurseId, status);
    }

    /**
     * Count overdue tasks for a nurse
     */
    public long countOverdueTasksByNurse(Long nurseId) {
        return taskRepository.countOverdueTasksByNurse(nurseId, LocalDate.now());
    }

    /**
     * Helper method to build description from predefined task and additional description
     */
    private String buildDescription(String predefinedDescription, String additionalDescription) {
        if (predefinedDescription == null && additionalDescription == null) {
            return null;
        }
        if (predefinedDescription == null) {
            return additionalDescription;
        }
        if (additionalDescription == null) {
            return predefinedDescription;
        }
        return predefinedDescription + "\n\nAdditional Notes: " + additionalDescription;
    }
}
