package com.example.hospital_backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/login",
                    "/actuator/mappings",
                    "/h2-console/**"
                ).permitAll()
                // Admin-only endpoints
                .requestMatchers("/api/**").hasRole("ADMIN")
                // Nurse and Admin access
                .requestMatchers("/api/nurses/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/tasks/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/notes/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/assignednurse/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/assigneddriver/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/clientmedication/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/clientnursehistory/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/assessmentinfo/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/previousclient/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/nursecertification/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/nursespecialization/**").hasAnyRole("ADMIN", "NURSE")
                .requestMatchers("/api/nurseworkday/**").hasAnyRole("ADMIN", "NURSE")
                // All authenticated users (ADMIN, NURSE, STAFF, USER)
                .requestMatchers("/api/items/**").hasAnyRole("ADMIN", "NURSE", "STAFF", "USER")
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


