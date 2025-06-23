// ===================================================================================
// FILE: src/main/java/com/example/taskapp/dto/AuthResponse.java
// ===================================================================================
package com.example.taskapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
}