package com.example.hospital_backend.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService TaskService;

    // Get all Tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> Tasks = TaskService.getAllTasks();
        return ResponseEntity.ok(Tasks);
    }

    // Get Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> Task = TaskService.getTaskById(id);
        return Task.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Task
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task Task) {
        try {
            Task savedTask = TaskService.saveTask(Task);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task Task) {
        Optional<Task> existingTask = TaskService.getTaskById(id);
        if (existingTask.isPresent()) {
            Task.setId(id); // Ensure the ID is set correctly
            Task updatedTask = TaskService.saveTask(Task);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> existingTask = TaskService.getTaskById(id);
        if (existingTask.isPresent()) {
            TaskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
