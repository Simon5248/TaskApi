// ===================================================================================
// FILE: src/main/java/com/example/taskapp/repository/TaskRepository.java
// ===================================================================================
package com.example.taskapp.repository;

import com.example.taskapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);
}
