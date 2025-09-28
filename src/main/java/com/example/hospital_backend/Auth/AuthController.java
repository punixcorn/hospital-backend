package com.example.hospital_backend.Auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital_backend.Security.JwtService;
import com.example.hospital_backend.User.User;
import com.example.hospital_backend.User.UserRepository;

import lombok.Data;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

 @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    // Always perform password hashing to prevent timing attacks
    String dummyHash = "$2a$10$dummy.hash.to.prevent.timing.attacks";
    
    Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
    
    boolean isValid = userOpt
        .map(user -> passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
        .orElseGet(() -> {
            // Perform dummy hash operation to maintain consistent timing
            passwordEncoder.matches(request.getPassword(), dummyHash);
            return false;
        });
    
    if (isValid && userOpt.isPresent()) {
        return issueToken(userOpt.get());
    }
    
    return ResponseEntity.status(401)
        .body(Map.of("error", "Invalid credentials"));
}

    private ResponseEntity<?> issueToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        String token = jwtService.generateToken(user.getEmail(), user.getId(), claims);
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole().name()));
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}


