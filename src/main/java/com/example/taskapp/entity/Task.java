// ===================================================================================
// FILE: src/main/java/com/example/taskapp/entity/Task.java
// ===================================================================================
package com.example.taskapp.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private String priority;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Long userId;
}
