package com.example.hospital_backend.AssignedNurse;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Nurse.Nurse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@jakarta.persistence.Table(name = "assigned_nurses")
public class AssignedNurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    private Integer hours;
}
