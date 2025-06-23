
// ===================================================================================
// FILE: src/main/java/com/example/taskapp/controller/AuthController.java
// ===================================================================================
package com.example.taskapp.controller;

import com.example.taskapp.dto.*;
import com.example.taskapp.entity.User;
import com.example.taskapp.repository.UserRepository;
import com.example.taskapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "此 Email 已被註冊。"));
        }
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "註冊成功！", "userId", savedUser.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "密碼錯誤。"));
        } catch (Exception e) {
             if (!userRepository.findByEmail(loginRequest.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "此 Email 尚未註冊。"));
             }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "認證失敗。"));
        }
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final User user = userRepository.findByEmail(loginRequest.getEmail()).get();
        final String jwt = jwtUtil.generateToken(userDetails, user.getId());
            
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}