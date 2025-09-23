// UserDTO.java
package com.example.hospital_backend.User;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String email;
    private UserRole role;
    private String phone;

    public Optional<User> toUser(){
        return Optional.of(User.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .name(name)
                .email(email)
                .role(role)
                .phone(phone)
                .build());
    }
}

