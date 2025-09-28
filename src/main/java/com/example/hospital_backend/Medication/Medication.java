package com.example.hospital_backend.Medication;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Client.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    @Column(unique = true, length = 255, nullable = false)
    private String name;

    @Column(length = 255)
    private String genericName;

    @Column(length = 100)
    private String dosageForm;

    @Column(length = 100)
    private String strength;
}
