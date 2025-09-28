package com.example.hospital_backend.Driver;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Nurse.Nurse;
import com.example.hospital_backend.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

public class Driver {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @lombok.NonNull
    @Column(length = 50, nullable = false)
    private String status;

    private Integer age;

    @Column(length = 100)
    private String carName;

    @Column(length = 50, unique = true)
    private String carNumber;

    @Column(length = 255)
    private String currentClient;

    @ManyToOne
    @JoinColumn(name = "current_nurse_id")
    private Nurse currentNurse;

    @Builder.Default
    private Integer hoursWorked = 0;

    @Builder.Default
    private Integer totalHoursAssigned = 0;

    private List<Long> assignedClients;
}
