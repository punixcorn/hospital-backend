package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.hospital_backend.Certification.CertificationDTO;
import com.example.hospital_backend.Client.ClientDTO;
import com.example.hospital_backend.Specialization.SpecializationDTO;
import com.example.hospital_backend.WorkDay.WorkDayDTO;

import lombok.*;

@Data
@Builder
public class NurseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String image;
    private Integer age;
    private Integer height;
    private Integer experience;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    private Double payRate;
    private Boolean available;
    private Long currentClient;
    private Integer hoursWorked;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalHoursAssigned;
    private Double payPerHour;
    private List<CertificationDTO> certifications;
    private List<SpecializationDTO> specializations;
    private List<WorkDayDTO> workDays;
    private List<ClientDTO> assignedClients;

    public Optional<Nurse> toNurse(){
        return Optional.of(Nurse.builder()
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