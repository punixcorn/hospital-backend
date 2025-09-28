package com.example.hospital_backend.WorkDay;
/*
x the workday
you see every nurse if they are assigned a client are assigned a period, say 
2 weeks 
so Mon - Sun then Mon - Sun 
the Workday is supposed to keep track of the number of days worked 
*/
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.hospital_backend.Nurse.Nurse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

public class WorkDay {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @lombok.NonNull
    @Column(unique = true, length = 20, nullable = false)
    private String dayName;

    @lombok.NonNull
    @Column(unique = true)
    private Integer dayNumber;

    @ManyToMany(mappedBy = "workDays")
    private List<Nurse> nurses;
}
