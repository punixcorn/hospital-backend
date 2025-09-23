package com.example.hospital_backend.Task;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository TaskRepository;

    public Optional<Task> getTaskById(Long id) {
        return TaskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return TaskRepository.findAll();
    }

    public Task saveTask(Task Task) {
        return TaskRepository.save(Task);
    }

    public void deleteTask(Long id) {
        TaskRepository.deleteById(id);
    }

}
