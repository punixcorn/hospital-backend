package com.example.hospital_backend.Nurse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * Request DTO for updating an existing nurse
 * 
 * All fields are optional for partial updates.
 * Validation rules apply when fields are provided.
 */
@Data
public class UpdateNurseRequest {
    
    //@Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String image;
    
    //@Min(value = 18, message = "Age must be at least 18")
    //@Max(value = 80, message = "Age cannot exceed 80")
    private Integer age;
    
    //@Min(value = 100, message = "Height must be at least 100 cm")
    //@Max(value = 250, message = "Height cannot exceed 250 cm")
    private Integer height;
    
    //@Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;
    
    private LocalTime shiftStart;
    
    private LocalTime shiftEnd;
    
    //@DecimalMin(value = "0.01", message = "Pay rate must be positive")
    private Double payRate;
    
    private Boolean available;
    
   // @Min(value = 0, message = "Current client ID cannot be negative")
    private Long currentClient;
    
    //@Min(value = 0, message = "Hours worked cannot be negative")
    private Integer hoursWorked;
    
    //@FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;
    
    // @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    //@Min(value = 0, message = "Total hours assigned cannot be negative")
    private Integer totalHoursAssigned;
    
   // @DecimalMin(value = "0.01", message = "Pay per hour must be positive")
    private Double payPerHour;
    
    private List<Long> certificationIds;
    
    private List<Long> specializationIds;
}
