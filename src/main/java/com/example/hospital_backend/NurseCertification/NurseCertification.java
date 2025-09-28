package com.example.hospital_backend.NurseCertification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Nurse.Nurse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor

public class NurseCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "certification_id", nullable = false)
    private Certification certification;

    @Column(length = 100)
    private String certNumber;

    @Column(columnDefinition = "DATE")
    private LocalDate issueDate;

    @Column(columnDefinition = "DATE")
    private LocalDate expiryDate;
}
