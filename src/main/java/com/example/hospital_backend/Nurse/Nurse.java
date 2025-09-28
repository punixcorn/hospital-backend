package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Certification.Certification;
import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.ClientNurseHistory.ClientNurseHistory;
import com.example.hospital_backend.Document.Document;
import com.example.hospital_backend.HourlyData.HourlyData;
import com.example.hospital_backend.Specialization.Specialization;
import com.example.hospital_backend.Task.Task;
import com.example.hospital_backend.WorkDay.WorkDay;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

public class Nurse {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    private Long userId;

    @Column(length = 500)
    private String image;

    private Integer age;
    private Integer height;
    private Integer experience;

    @Column(columnDefinition = "TIME")
    private LocalTime shiftStart;

    @Column(columnDefinition = "TIME")
    private LocalTime shiftEnd;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payRate;

    @Builder.Default
    private Boolean available = true;

    @lombok.NonNull
    private Long currentClient;

    @Builder.Default
    private Integer hoursWorked = 0;

    @Column(columnDefinition = "DATE", nullable = true)
    private LocalDate startDate;

    @Column(columnDefinition = "DATE", nullable = true)
    private LocalDate endDate;

    @Builder.Default
    private Integer totalHoursAssigned = 0;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payPerHour;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Certification> certifications;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Specialization> specializations;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<WorkDay> workDays;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Client> previousClients;

    private List<Long> notes;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Task> tasks;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<HourlyData> hourlyData;

    private List<Long> assignedClients;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientNurseHistory> clientHistories;

    public Optional<NurseDTO> toNurseDTO(){
        return Optional.of(NurseDTO.builder()
            .id(id)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .userId(userId)
            .image(image)
            .age(age)
            .height(height)
            .experience(experience)
            .shiftStart(shiftStart)
            .shiftEnd(shiftEnd)
            .payRate(payRate)
            .available(available)
            .currentClient(currentClient)
            .hoursWorked(hoursWorked)
            .startDate(startDate)
            .endDate(endDate)
            .totalHoursAssigned(totalHoursAssigned)
            .payPerHour(payPerHour)
            .build());   
    }
}
