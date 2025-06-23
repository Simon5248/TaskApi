
// ===================================================================================
// FILE: src/main/java/com/example/taskapp/controller/TaskController.java
// ===================================================================================
package com.example.taskapp.controller;

import com.example.taskapp.entity.Task;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private Long getUserIdFromAuthentication(Authentication authentication) {
        return (Long) authentication.getPrincipal();
    }

    @GetMapping
    public List<Task> getTasks(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication authentication) {
        task.setUserId(getUserIdFromAuthentication(authentication));
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return taskRepository.findById(id)
                .map(task -> {
                    if (!task.getUserId().equals(userId)) {
                        return new ResponseEntity<Task>(HttpStatus.FORBIDDEN);
                    }
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setStatus(taskDetails.getStatus());
                    task.setPriority(taskDetails.getPriority());
                    Task updatedTask = taskRepository.save(task);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return taskRepository.findById(id)
                .map(task -> {
                    if (!task.getUserId().equals(userId)) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                    taskRepository.delete(task);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}