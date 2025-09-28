package com.example.hospital_backend.Initializers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Client.ClientRepository;
import com.example.hospital_backend.Item.Item;
import com.example.hospital_backend.Item.ItemRepository;
import com.example.hospital_backend.Nurse.Nurse;
import com.example.hospital_backend.Nurse.NurseRepository;
import com.example.hospital_backend.User.User;
import com.example.hospital_backend.User.UserRepository;
import com.example.hospital_backend.User.UserRole;

@Component
public class InitUsers implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NurseRepository nurseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
    // Create 10 users
    {
    List<User> users = List.of(
        User.builder()
            .name("Sarah Johnson")
            .email("sarah.johnson@hospital.com")
            .passwordHash(passwordEncoder.encode("password1"))
            .role(UserRole.NURSE)
            .phone("555-0101")
            .build(),
        
        User.builder()
            .name("Michael Chen")
            .email("michael.chen@hospital.com")
            .passwordHash(passwordEncoder.encode("password2"))
            .role(UserRole.NURSE)
            .phone("555-0102")
            .build(),
        
        User.builder()
            .name("Emily Rodriguez")
            .email("emily.rodriguez@hospital.com")
            .passwordHash(passwordEncoder.encode("password3"))
            .role(UserRole.NURSE)
            .phone("555-0103")
            .build(),
        
        User.builder()
            .name("David Wilson")
            .email("david.wilson@hospital.com")
            .passwordHash(passwordEncoder.encode("password4"))
            .role(UserRole.NURSE)
            .phone("555-0104")
            .build(),
        
        User.builder()
            .name("Lisa Thompson")
            .email("lisa.thompson@hospital.com")
            .passwordHash(passwordEncoder.encode("password5"))
            .role(UserRole.NURSE)
            .phone("555-0105")
            .build(),
        
        User.builder()
            .name("Admin User")
            .email("admin@hospital.com")
            .passwordHash(passwordEncoder.encode("admin"))
            .role(UserRole.ADMIN)
            .phone("555-0001")
            .build(),
        
        User.builder()
            .name("Dr. James Miller")
            .email("james.miller@hospital.com")
            .passwordHash(passwordEncoder.encode("doctor"))
            .role(UserRole.STAFF)
            .phone("555-0201")
            .build(),
        
        User.builder()
            .name("Maria Garcia")
            .email("maria.garcia@hospital.com")
            .passwordHash("hashed_password_6")
            .role(UserRole.STAFF)
            .phone("555-0301")
            .build(),
        
        User.builder()
            .name("Robert Brown")
            .email("robert.brown@hospital.com")
            .passwordHash("hashed_password_7")
            .role(UserRole.STAFF)
            .phone("555-0302")
            .build(),
        
        User.builder()
            .name("Jennifer Lee")
            .email("jennifer.lee@hospital.com")
            .passwordHash("hashed_password_8")
            .role(UserRole.STAFF)
            .phone("555-0303")
            .build()
    );
    for (User user : users) {
        userRepository.save(user);
    }
    // create 10 nurses
    
    List<Nurse> nurses = List.of(
        Nurse.builder()
            .userId(users.get(0).getId())
            .age(32)
            .height(165)
            .experience(8)
            .shiftStart(LocalTime.of(7, 0))
            .shiftEnd(LocalTime.of(15, 0))
            .payRate(45.50)
            .available(true)
            .currentClient(1L)
            .hoursWorked(160)
            .startDate(LocalDate.of(2023, 1, 15))
            .endDate(LocalDate.of(2024, 1, 15))
            .totalHoursAssigned(200)
            .payPerHour(42.00)
            .build(),
        
        Nurse.builder()
            .userId(users.get(1).getId())
            .age(28)
            .height(178)
            .experience(5)
            .shiftStart(LocalTime.of(15, 0))
            .shiftEnd(LocalTime.of(23, 0))
            .payRate(38.75)
            .available(true)
            .currentClient(2L)
            .hoursWorked(120)
            .startDate(LocalDate.of(2023, 3, 10))
            .endDate(LocalDate.of(2024, 3, 10))
            .totalHoursAssigned(180)
            .payPerHour(36.50)
            .build(),
        
        Nurse.builder()
            .userId(users.get(2).getId())
            .age(45)
            .height(162)
            .experience(20)
            .shiftStart(LocalTime.of(23, 0))
            .shiftEnd(LocalTime.of(7, 0))
            .payRate(52.00)
            .available(false)
            .currentClient(0L)
            .hoursWorked(240)
            .startDate(LocalDate.of(2022, 6, 1))
            .endDate(LocalDate.of(2024, 6, 1))
            .totalHoursAssigned(250)
            .payPerHour(48.00)
            .build(),
        
        Nurse.builder()
            .userId(users.get(3).getId())
            .age(29)
            .height(170)
            .experience(6)
            .shiftStart(LocalTime.of(8, 0))
            .shiftEnd(LocalTime.of(16, 0))
            .payRate(40.25)
            .available(true)
            .currentClient(3L)
            .hoursWorked(140)
            .startDate(LocalDate.of(2023, 5, 20))
            .endDate(LocalDate.of(2024, 5, 20))
            .totalHoursAssigned(190)
            .payPerHour(38.00)
            .build(),
        
        Nurse.builder()
            .userId(users.get(4).getId())
            .age(35)
            .height(168)
            .experience(12)
            .shiftStart(LocalTime.of(12, 0))
            .shiftEnd(LocalTime.of(20, 0))
            .payRate(47.80)
            .available(true)
            .currentClient(4L)
            .hoursWorked(180)
            .startDate(LocalDate.of(2022, 11, 5))
            .endDate(LocalDate.of(2024, 11, 5))
            .totalHoursAssigned(220)
            .payPerHour(44.50)
            .build()
    );
    
    for (Nurse nurse : nurses) {
        nurseRepository.save(nurse);
    }
    // Create 5 clients
    List<Client> clients = List.of(
        Client.builder()
            .name("John Smith")
            .status("Active")
            .location("123 Main St, Anytown")
            .coordinatesLat(40.712776)
            .coordinatesLng(-74.005974)
            .message("Regular checkups needed")
            .description("Elderly patient with diabetes and hypertension")
            .dateReachout(LocalDate.of(2024, 1, 10))
            .dateWanted(LocalDate.of(2024, 1, 15))
            .serviceType("Home Care")
            .clientLinkID("client-001-abc-xyz")
            .age(72)
            .height("5'9\"")
            .phone("555-1001")
            .deceased(false)
            .condition("Diabetes, Hypertension")
            .insurance("Medicare")
            .emergencyContact("Mary Smith - 555-1002")
            .build(),
        
        Client.builder()
            .name("Alice Johnson")
            .status("Pending")
            .location("456 Oak Ave, Somewhere")
            .coordinatesLat(34.052235)
            .coordinatesLng(-118.243683)
            .message("Post-surgery recovery")
            .description("Recent hip replacement surgery, needs mobility assistance")
            .dateReachout(LocalDate.of(2024, 2, 5))
            .dateWanted(LocalDate.of(2024, 2, 10))
            .serviceType("Rehabilitation")
            .clientLinkID("client-002-def-uvw")
            .age(68)
            .height("5'4\"")
            .phone("555-1003")
            .deceased(false)
            .condition("Post-operative care")
            .insurance("Blue Cross")
            .emergencyContact("Bob Johnson - 555-1004")
            .build(),
        
        Client.builder()
            .name("Robert Davis")
            .status("Active")
            .location("789 Pine Rd, Nowhere")
            .coordinatesLat(41.878113)
            .coordinatesLng(-87.629799)
            .message("Chronic condition management")
            .description("COPD patient requiring oxygen therapy and medication management")
            .dateReachout(LocalDate.of(2024, 1, 20))
            .dateWanted(LocalDate.of(2024, 1, 25))
            .serviceType("Chronic Care")
            .clientLinkID("client-003-ghi-rst")
            .age(75)
            .height("5'11\"")
            .phone("555-1005")
            .deceased(false)
            .condition("COPD, Arthritis")
            .insurance("Aetna")
            .emergencyContact("Susan Davis - 555-1006")
            .build(),
        
        Client.builder()
            .name("Margaret Wilson")
            .status("Inactive")
            .location("321 Elm St, Anycity")
            .coordinatesLat(39.739235)
            .coordinatesLng(-104.990250)
            .message("Palliative care")
            .description("Terminal cancer patient requiring comfort care and pain management")
            .dateReachout(LocalDate.of(2023, 12, 1))
            .dateWanted(LocalDate.of(2023, 12, 5))
            .serviceType("Hospice Care")
            .clientLinkID("client-004-jkl-mno")
            .age(82)
            .height("5'2\"")
            .phone("555-1007")
            .deceased(false)
            .condition("Terminal Cancer")
            .insurance("Medicaid")
            .emergencyContact("Tom Wilson - 555-1008")
            .build(),
        
        Client.builder()
            .name("Charles Brown")
            .status("Active")
            .location("654 Maple Dr, Yourtown")
            .coordinatesLat(32.715736)
            .coordinatesLng(-117.161087)
            .message("Physical therapy needed")
            .description("Stroke recovery patient requiring physical and occupational therapy")
            .dateReachout(LocalDate.of(2024, 2, 15))
            .dateWanted(LocalDate.of(2024, 2, 20))
            .serviceType("Therapy")
            .clientLinkID("client-005-pqr-stu")
            .age(65)
            .height("6'0\"")
            .phone("555-1009")
            .deceased(false)
            .condition("Post-stroke recovery")
            .insurance("United Healthcare")
            .emergencyContact("Linda Brown - 555-1010")
            .build()
    );


    for (Client client : clients) {

        clientRepository.save(client);
    }
}   

    //create 10 items
    {
        List<Item> Items = List.of(
            Item.builder().id(1L).inventory_id(1001L).quantity(5).build(),
            Item.builder().id(2L).inventory_id(1002L).quantity(3).build(),
            Item.builder().id(3L).inventory_id(1003L).quantity(10).build(),
            Item.builder().id(4L).inventory_id(1004L).quantity(2).build(),
            Item.builder().id(5L).inventory_id(1005L).quantity(8).build(),
            Item.builder().id(6L).inventory_id(1006L).quantity(1).build(),
            Item.builder().id(7L).inventory_id(1007L).quantity(15).build(),
            Item.builder().id(8L).inventory_id(1008L).quantity(4).build(),
            Item.builder().id(9L).inventory_id(1009L).quantity(6).build(),
            Item.builder().id(10L).inventory_id(1010L).quantity(12).build(),
            Item.builder().id(11L).inventory_id(1011L).quantity(7).build(),
            Item.builder().id(12L).inventory_id(1012L).quantity(9).build(),
            Item.builder().id(13L).inventory_id(1013L).quantity(3).build(),
            Item.builder().id(14L).inventory_id(1014L).quantity(20).build(),
            Item.builder().id(15L).inventory_id(1015L).quantity(11).build(),
            Item.builder().id(16L).inventory_id(1016L).quantity(5).build(),
            Item.builder().id(17L).inventory_id(1017L).quantity(14).build(),
            Item.builder().id(18L).inventory_id(1018L).quantity(2).build(),
            Item.builder().id(19L).inventory_id(1019L).quantity(8).build(),
            Item.builder().id(20L).inventory_id(1020L).quantity(6).build()
        );

        for (var item : Items){
            itemRepository.save(item);
        }
    }


    }
}

/* 
 * 
 * 
 * // ============= CORE ENTITIES (PII ONLY) =============

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Core PII - what defines a client
    @NonNull
    @Column(nullable = false)
    private String name;
    
    private Integer age;
    
    @Column(unique = true)
    private String phone;
    
    private String location;
    
    @Column(columnDefinition = "DECIMAL(10,8)")
    private Double coordinatesLat;
    
    @Column(columnDefinition = "DECIMAL(11,8)")
    private Double coordinatesLng;
    
    private String emergencyContact;
    
    // Medical basics
    private String condition;
    private String insurance;
    private String height;
    
    @Builder.Default
    private Boolean deceased = false;
    
    @Column(unique = true)
    private String clientLinkID;
    
    // Additional client fields
    private String gender;
    private String email;
    
    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    
    private String bloodType;
    private String allergies;
    
    @Builder.Default
    private Boolean active = true;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Core PII - what defines a nurse
    @NonNull
    private Long userId; // Link to user management system
    
    @Column(nullable = false)
    private String name;
    
    private String image;
    private Integer age;
    private Integer height;
    private Integer experience;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double basePayRate;
    
    // Basic availability
    @Builder.Default
    private Boolean active = true;
    
    // Additional nurse fields
    private String phone;
    private String email;
    private String address;
    private String licenseNumber;
    
    @Column(columnDefinition = "DATE")
    private LocalDate licenseExpiryDate;
    
    @Column(columnDefinition = "DATE")
    private LocalDate hireDate;
    
    private String employmentStatus; // FULL_TIME, PART_TIME, CONTRACT
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @NonNull
    private Long userId; // Link to user management system
    
    @Column(nullable = false)
    private String name;
    
    private String phone;
    private String email;
    private String licenseNumber;
    
    @Column(columnDefinition = "DATE")
    private LocalDate licenseExpiryDate;
    
    private String vehicleType;
    private String vehiclePlate;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double basePayRate;
    
    @Builder.Default
    private Boolean active = true;
    
    @Column(columnDefinition = "DATE")
    private LocalDate hireDate;
}

// ============= OPERATIONAL ENTITIES =============

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private Long clientId;
    
    @Column(nullable = false)
    private String status; // PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    
    private String serviceType;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column(columnDefinition = "DATE")
    private LocalDate dateRequested;
    
    @Column(columnDefinition = "DATE")
    private LocalDate dateWanted;
    
    private Long assignedNurseId;
    private Long assignedDriverId;
    
    private String priority; // LOW, MEDIUM, HIGH, URGENT
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double estimatedCost;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private Long nurseId;
    private Long clientId;
    private Long serviceRequestId;
    
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    
    private String status; // ACTIVE, COMPLETED, CANCELLED
    
    @Builder.Default
    private Integer hoursWorked = 0;
    
    @Builder.Default
    private Integer totalHoursAssigned = 0;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payRate;
    
    private String assignmentType; // TEMPORARY, PERMANENT, ONE_TIME
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private Long driverId;
    private Long clientId;
    private Long serviceRequestId;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime scheduledDateTime;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime actualStartTime;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime actualEndTime;
    
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    
    private String deliveryType; // EQUIPMENT, MEDICATION, SUPPLIES
    
    @Column(columnDefinition = "TEXT")
    private String deliveryDetails;
    
    private String pickupAddress;
    private String deliveryAddress;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payRate;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long nurseId;
    
    @Column(columnDefinition = "TIME")
    private LocalTime shiftStart;
    
    @Column(columnDefinition = "TIME")
    private LocalTime shiftEnd;
    
    private String dayOfWeek; // MONDAY, TUESDAY, etc.
    
    @Builder.Default
    private Boolean active = true;
    
    private String shiftType; // DAY, NIGHT, SWING
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long nurseId;
    
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;
    
    @Column(columnDefinition = "TIME")
    private LocalTime endTime;
    
    @Builder.Default
    private Boolean available = true;
    
    private String reason; // VACATION, SICK, ASSIGNED, etc.
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long clientId;
    
    @Column(nullable = false)
    private String medicationName;
    
    private String dosage;
    private String frequency;
    private String instructions;
    private String prescribedBy;
    
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    
    @Builder.Default
    private Boolean active = true;
    
    private String medicationType; // PRESCRIPTION, OTC, SUPPLEMENT
    private String sideEffects;
    private String contraindications;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private Long nurseId;
    private Long clientId;
    private Long assignmentId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String status; // PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    
    private String priority; // LOW, MEDIUM, HIGH, URGENT
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dueDate;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime completedAt;
    
    private String taskType; // MEDICATION, ASSESSMENT, MONITORING, PERSONAL_CARE
    
    @Column(columnDefinition = "TEXT")
    private String completionNotes;
    
    private Long createdBy; // Admin or Nurse ID who created the task
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private Long nurseId;
    private Long clientId;
    private Long assignmentId;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    private String noteType; // OBSERVATION, MEDICATION, INCIDENT, GENERAL, ASSESSMENT
    
    @Builder.Default
    private Boolean isPrivate = false;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime observationTime;
    
    private String mood;
    private String behavior;
    private String physicalCondition;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long nurseId;
    
    @Column(nullable = false)
    private String certificationName;
    
    private String issuingOrganization;
    
    @Column(columnDefinition = "DATE")
    private LocalDate issueDate;
    
    @Column(columnDefinition = "DATE")
    private LocalDate expiryDate;
    
    private String certificationNumber;
    
    @Builder.Default
    private Boolean active = true;
    
    private String certificationLevel; // BASIC, INTERMEDIATE, ADVANCED
    private String filePath; // Path to certification document
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseSpecialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long nurseId;
    
    @Column(nullable = false)
    private String specializationName;
    
    private String description;
    
    @Column(columnDefinition = "DATE")
    private LocalDate acquiredDate;
    
    private Integer experienceYears;
    
    @Builder.Default
    private Boolean active = true;
    
    private String proficiencyLevel; // BEGINNER, INTERMEDIATE, EXPERT
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NurseRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private Long nurseId;
    private Long ratedBy; // Admin user ID
    private Long assignmentId;
    
    @Column(nullable = false)
    private Integer rating; // 1-5 scale
    
    @Column(columnDefinition = "TEXT")
    private String comments;
    
    private String ratingCategory; // OVERALL, PUNCTUALITY, PROFESSIONALISM, CARE_QUALITY, COMMUNICATION
    
    @Column(columnDefinition = "DATE")
    private LocalDate ratingPeriodStart;
    
    @Column(columnDefinition = "DATE")
    private LocalDate ratingPeriodEnd;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private Long nurseId;
    private Long clientId;
    private Long assignmentId;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime clockIn;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime clockOut;
    
    @Column(columnDefinition = "DECIMAL(4,2)")
    private Double hoursWorked;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double payRate;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double totalPay;
    
    private String status; // ACTIVE, COMPLETED, DISPUTED
    
    private String location; // Where they clocked in/out
    
    @Column(columnDefinition = "DECIMAL(10,8)")
    private Double clockInLat;
    
    @Column(columnDefinition = "DECIMAL(11,8)")
    private Double clockInLng;
    
    @Column(columnDefinition = "DECIMAL(10,8)")
    private Double clockOutLat;
    
    @Column(columnDefinition = "DECIMAL(11,8)")
    private Double clockOutLng;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}

// ============= ASSESSMENT AND CLINICAL ENTITIES =============

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private Long clientId;
    private Long nurseId;
    private Long assignmentId;
    
    private String assessmentType; // INITIAL, PERIODIC, DISCHARGE, EMERGENCY
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime assessmentDate;
    
    // Vital Signs
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double temperature;
    
    private String bloodPressureSystolic;
    private String bloodPressureDiastolic;
    
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double heartRate;
    
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double respiratoryRate;
    
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double oxygenSaturation;
    
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double weight;
    
    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double height;
    
    // Clinical Observations
    @Column(columnDefinition = "TEXT")
    private String physicalAppearance;
    
    @Column(columnDefinition = "TEXT")
    private String mentalState;
    
    @Column(columnDefinition = "TEXT")
    private String mobility;
    
    @Column(columnDefinition = "TEXT")
    private String painLevel;
    
    @Column(columnDefinition = "TEXT")
    private String skinCondition;
    
    @Column(columnDefinition = "TEXT")
    private String respiratoryObservations;
    
    @Column(columnDefinition = "TEXT")
    private String cardiovascularObservations;
    
    @Column(columnDefinition = "TEXT")
    private String neurologicalObservations;
    
    @Column(columnDefinition = "TEXT")
    private String gastrointestinalObservations;
    
    @Column(columnDefinition = "TEXT")
    private String genitourinaryObservations;
    
    // Care Plan
    @Column(columnDefinition = "TEXT")
    private String carePlan;
    
    @Column(columnDefinition = "TEXT")
    private String recommendations;
    
    @Column(columnDefinition = "TEXT")
    private String followUpInstructions;
    
    private String status; // DRAFT, COMPLETED, REVIEWED
    
    private Long reviewedBy; // Supervising nurse or admin
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime reviewedAt;
}

// ============= INVENTORY AND PURCHASE ENTITIES =============

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String category; // EQUIPMENT, MEDICATION, SUPPLIES
    
    private String subCategory;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double price;
    
    private String sku;
    
    @Builder.Default
    private Boolean active = true;
    
    @Builder.Default
    private Boolean requiresPrescription = false;
    
    private String manufacturer;
    private String model;
    private String specifications;
    
    @Builder.Default
    private Integer stockQuantity = 0;
    
    private Integer reorderLevel;
    private String unit; // PIECE, BOX, BOTTLE, etc.
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private Long clientId;
    private Long orderedBy; // Could be nurse, admin, or client user ID
    
    private String orderNumber;
    
    private String status; // PENDING, APPROVED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double totalAmount;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double taxAmount;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double shippingAmount;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double grandTotal;
    
    private String paymentStatus; // PENDING, PAID, REFUNDED
    
    private String paymentMethod;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime orderDate;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime expectedDeliveryDate;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime actualDeliveryDate;
    
    private String deliveryAddress;
    
    @Column(columnDefinition = "TEXT")
    private String specialInstructions;
    
    private Long assignedDriverId; // For delivery
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long purchaseId;
    private Long productId;
    
    private Integer quantity;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double unitPrice;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double totalPrice;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    private String status; // ORDERED, BACKORDERED, SHIPPED, DELIVERED
}

// ============= HISTORICAL TRACKING ENTITIES =============

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientNurseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime recordedAt;
    
    private Long clientId;
    private Long nurseId;
    private Long assignmentId;
    
    private String relationshipType; // ASSIGNED, UNASSIGNED, TRANSFERRED
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime startDateTime;
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime endDateTime;
    
    @Column(columnDefinition = "TEXT")
    private String reason;
    
    private Integer totalHoursWorked;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double averageRating;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
}

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDriverHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime recordedAt;
    
    private Long clientId;
    private Long driverId;
    private Long assignmentId;
    
    private String serviceType; // DELIVERY, TRANSPORT, EMERGENCY
    
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime serviceDateTime;
    
    @Column(columnDefinition = "TEXT")
    private String serviceDetails;
    
    private String status; // COMPLETED, CANCELLED, FAILED
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double serviceRating;
    
    @Column(columnDefinition = "TEXT")
    private String feedback;
}
*/