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
import com.example.hospital_backend.Note.Note;
import com.example.hospital_backend.PreviousClient.PreviousClient;
import com.example.hospital_backend.Specialization.Specialization;
import com.example.hospital_backend.Task.Task;
import com.example.hospital_backend.WorkDay.WorkDay;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@jakarta.persistence.Table(name = "nurses")
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

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;

    @Builder.Default
    private Integer totalHoursAssigned = 0;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payPerHour;

    @ManyToMany
    @JoinTable(name = "nurse_certifications", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "certification_id"))
    private List<Certification> certifications;

    @ManyToMany
    @JoinTable(name = "nurse_specializations", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private List<Specialization> specializations;

    @ManyToMany
    @JoinTable(name = "nurse_work_days", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "work_day_id"))
    private List<WorkDay> workDays;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreviousClient> previousClients;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HourlyData> hourlyData;

    @ManyToMany
    @JoinTable(name = "assigned_nurses", joinColumns = @JoinColumn(name = "nurse_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> assignedClients;

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
