package com.example.hospital_backend.User;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private UserRole role = UserRole.USER;
    private String phone;
}