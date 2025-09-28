package com.example.hospital_backend.Item;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// inventory holds like static items, like 
// a bed, a table, a chair which all have an inventory_id
// a client can have many items which contains the inventory_id and the quantity
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor

public class Item {
    @jakarta.persistence.Id
    private Long id;

    @NonNull
    private Long inventory_id;

    @Builder.Default
    private Integer quantity = 1;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}