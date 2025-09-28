package com.example.hospital_backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.hospital_backend.User.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SecurityUtils {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    /**
     * Get current authenticated user's ID from JWT token
     */
    public Long getCurrentUserId() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                return jwtService.extractUserId(token);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Get current authenticated user's username
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    /**
     * Get current user ID by looking up username in database (fallback method)
     */
    public Long getCurrentUserIdFromDatabase() {
        String username = getCurrentUsername();
        if (username != null) {
            return userRepository.findByEmail(username)
                .map(user -> user.getId())
                .orElse(null);
        }
        return null;
    }

    /**
     * Check if current user has specific role
     */
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
        }
        return false;
    }

    /**
     * Check if current user is admin
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    /**
     * Check if the current user ID matches the given user ID
     */
    public boolean isCurrentUser(Long userId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }
}