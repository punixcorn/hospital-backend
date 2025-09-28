package com.example.hospital_backend.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hospital_backend.Security.CustomUserDetailsService;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> getAllUserByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }

    public Optional<User> createUser(CreateUserRequest request) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return Optional.of(userRepository.save(User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .phone(request.getPhone())
            .build()));
    }

    public Optional<User> updateUser(Long id, CreateUserRequest request) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (request.getName() != null) {
                user.setName(request.getName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getPassword() != null) {
                user.setPasswordHash(request.getPassword());
            }
            if (request.getRole() != null) {
                user.setRole(request.getRole());
            }
            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
            }
            return Optional.of(userRepository.save(user));
        }
        return userOpt;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validateUser(String email, String password) {
        Optional<User> userOpt = getUserByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getPasswordHash().equals(password);
        }
        return false;
    }

   public boolean changePassword (ChangePasswordRequest changePasswordRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (!userDetails.getPassword().equals(oldPassword)) {
            return false;
        }

        Optional<User> userOpt = getUserByEmail(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPasswordHash(newPassword);
            return userRepository.save(user) != null;
        }
        return false;
    }
}
