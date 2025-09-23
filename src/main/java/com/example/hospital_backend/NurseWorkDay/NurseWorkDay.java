package com.example.hospital_backend.NurseWorkDay;

import com.example.hospital_backend.Nurse.Nurse;
import com.example.hospital_backend.WorkDay.WorkDay;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Table(name = "nurse_work_days")
public class NurseWorkDay {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "work_day_id")
    private WorkDay workDay;
}
