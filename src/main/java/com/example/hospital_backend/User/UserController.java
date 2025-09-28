package com.example.hospital_backend.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Nonnull;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request).map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public List<User> getAllusers() {
        return userService.getAllUsers();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @Nonnull Long id,@RequestBody CreateUserRequest request) {
        return userService.updateUser(id, request).map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/roles")
    public ResponseEntity<List<User>> getUserById(@RequestParam(name = "role", required = true)UserRole role) {
        if (role == null) {
        return ResponseEntity.badRequest().build();
        }

        List<User> userList = userService.getAllUserByRole(role);
        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/name")
    public ResponseEntity<User> getUserByName(@RequestParam(name ="name" ,required = true) String name) {
        Optional<User> user = userService.getUserByName(name);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam(name ="email" ,required = true) String email) {
        return userService.getUserByEmail(email).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone")
    public ResponseEntity<User> getUserByPhone(@RequestParam(name ="number" ,required = true) String phone) {
        return userService.getUserByPhone(phone).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest) ? ResponseEntity.ok("Password changed successfully") : ResponseEntity.badRequest().build();
    }
    
    
}