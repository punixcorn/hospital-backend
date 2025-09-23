package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.AssessmentInfo.AssessmentInfo;
import com.example.hospital_backend.ClientNurseHistory.ClientNurseHistory;
import com.example.hospital_backend.Driver.Driver;
import com.example.hospital_backend.Item.Item;
import com.example.hospital_backend.Medication.Medication;
import com.example.hospital_backend.Note.Note;
import com.example.hospital_backend.Nurse.Nurse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@jakarta.persistence.Table(name = "clients")
public class Client {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    @Column(length = 255, nullable = false)
    private String name;

    @lombok.NonNull
    @Column(length = 50, nullable = false)
    private String status;

    @Column(length = 500)
    private String location;

    @Column(columnDefinition = "DECIMAL(10,8)")
    private Double coordinatesLat;

    @Column(columnDefinition = "DECIMAL(11,8)")
    private Double coordinatesLng;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate dateReachout;

    @Column(columnDefinition = "DATE")
    private LocalDate dateWanted;

    @Column(length = 100)
    private String serviceType;

    @Column(columnDefinition = "CHAR(36)", unique = true)
    private String clientLinkID;

    private Integer age;

    @Column(length = 10)
    private String height;

    @Column(length = 20, unique = true)
    private String phone;

    @Builder.Default
    private Boolean deceased = false;

    @Column(length = 500)
    private String condition;

    @Column(length = 100)
    private String insurance;

    @Column(length = 500)
    private String emergencyContact;

    @ManyToMany
    @JoinTable(name = "client_medications", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<Medication> medications;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientNurseHistory> nurseHistories;

    @ManyToMany(mappedBy = "assignedClients")
    private List<Nurse> assignedNurses;

    @ManyToMany(mappedBy = "assignedClients")
    private List<Driver> assignedDrivers;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private AssessmentInfo assessmentInfo;
}
