package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import lombok.Data;

/**
 * Request DTO for creating a new nurse
 * 
 * Required fields:
 * - userId: Must reference an existing user with NURSE role
 * - age: Must be between 18 and 80
 * - height: Must be between 100 and 250 cm
 * - experience: Must be non-negative
 * - startDate: Must be in the future or today
 * - endDate: Must be after startDate
 * - payRate: Must be positive
 * - payPerHour: Must be positive
 * 
 * Optional fields:
 * - image: Profile image URL
 * - shiftStart/shiftEnd: Work shift times
 * - available: Defaults to true
 * - currentClient: Defaults to 0 (no client assigned)
 * - hoursWorked: Defaults to 0
 * - totalHoursAssigned: Defaults to 0
 * - certificationIds: List of certification IDs
 * - specializationIds: List of specialization IDs
 */
@Data
public class CreateNurseRequest {
    
    //@NotNull(message = "User ID is required")
    //@Positive(message = "User ID must be positive")
    private Long userId;
    
    //@Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String image;
    
    //@NotNull(message = "Age is required")
    //@Min(value = 18, message = "Age must be at least 18")
    //@Max(value = 80, message = "Age cannot exceed 80")
    private Integer age;
    
    //@NotNull(message = "Height is required")
    //@Min(value = 100, message = "Height must be at least 100 cm")
    //@Max(value = 250, message = "Height cannot exceed 250 cm")
    private Integer height;
    
    //@NotNull(message = "Experience is required")
    //@Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;
    
    private LocalTime shiftStart;
    
    private LocalTime shiftEnd;
    
    //@NotNull(message = "Pay rate is required")
    //@DecimalMin(value = "0.01", message = "Pay rate must be positive")
    private Double payRate;
    
    private Boolean available = true;
    
    private Long currentClient = 0L;
    
    private Integer hoursWorked = 0;
    
    //@NotNull(message = "Start date is required")
    //@FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;
    
    //@NotNull(message = "End date is required")
    //@Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    private Integer totalHoursAssigned = 0;
    
    //@NotNull(message = "Pay per hour is required")
    //@DecimalMin(value = "0.01", message = "Pay per hour must be positive")
    private Double payPerHour;
    
    private List<Long> certificationIds;
    
    private List<Long> specializationIds;
}
