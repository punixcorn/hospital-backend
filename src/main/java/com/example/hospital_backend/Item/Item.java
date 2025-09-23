package com.example.hospital_backend.Item;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Client.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@jakarta.persistence.Table(name = "items")
public class Item {
    @jakarta.persistence.Id
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @lombok.NonNull
    @Column(length = 255, nullable = false)
    private String name;

    @Builder.Default
    private Integer quantity = 1;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
