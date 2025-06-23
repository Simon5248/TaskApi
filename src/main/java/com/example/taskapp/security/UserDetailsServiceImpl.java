// ===================================================================================
// FILE: src/main/java/com/example/taskapp/security/UserDetailsServiceImpl.java
// ===================================================================================
package com.example.taskapp.security;

import com.example.taskapp.entity.User;
import com.example.taskapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("找不到用戶: " + email));
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), 
            user.getPassword(), 
            Collections.emptyList() // 在此處可以添加角色
        );
    }
}