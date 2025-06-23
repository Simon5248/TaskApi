// ===================================================================================
// FILE: src/main/java/com/example/taskapp/dto/RegisterRequest.java
// ===================================================================================
package com.example.taskapp.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
