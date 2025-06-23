// ===================================================================================
// FILE: src/main/java/com/example/taskapp/repository/UserRepository.java
// ===================================================================================
package com.example.taskapp.repository;

import com.example.taskapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
