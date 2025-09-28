package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.hospital_backend.ClientNurseHistory.ClientNurseHistory;
import com.example.hospital_backend.Driver.Driver;
import com.example.hospital_backend.Item.Item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import com.example.hospital_backend.Nurse.Nurse;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private List<Long> medications = new ArrayList<>();

    @OneToMany
    private List<Nurse> nurseHistories;

    @Builder.Default
    private List<Nurse> assignedNurses = new ArrayList<>();

    @Builder.Default
    private List<Driver> assignedDrivers = new ArrayList<>();

    @Builder.Default
    private List<Item> items = new ArrayList<>();

    private Long assessmentInfoId;

    /**
     * Convert Client entity to ClientDTO
     */
    public Optional<ClientDTO> toClientDTO() {
        return Optional.of(ClientDTO.builder()
            .id(id)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .name(name)
            .status(status)
            .location(location)
            .coordinatesLat(coordinatesLat)
            .coordinatesLng(coordinatesLng)
            .message(message)
            .description(description)
            .dateReachout(dateReachout)
            .dateWanted(dateWanted)
            .serviceType(serviceType)
            .clientLinkID(clientLinkID)
            .age(age)
            .height(height)
            .phone(phone)
            .deceased(deceased)
            .condition(condition)
            .insurance(insurance)
            .assessmentInfoId(assessmentInfoId)
            .emergencyContact(emergencyContact)
            .build());
    }
}
