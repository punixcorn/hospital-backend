package com.example.hospital_backend.AssessmentInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Nurse.Nurse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@jakarta.persistence.Table(name = "assessment_info")
public class AssessmentInfo {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @lombok.NonNull
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate assessmentDate;

    @Column(columnDefinition = "TEXT")
    private String mobility;

    @Column(columnDefinition = "TEXT")
    private String medications;

    @Column(columnDefinition = "TEXT")
    private String specialNeeds;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @lombok.NonNull
    @Column(length = 50, nullable = false)
    @Builder.Default
    private String status = "active";

    @lombok.NonNull
    private Long createdBy;
}
