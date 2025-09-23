package com.example.hospital_backend.Initializers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Client.ClientRepository;
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

    @Override
    public void run(String... args) throws Exception {

    {
    // Create 10 users
    List<User> users = List.of(
        User.builder()
            .name("Sarah Johnson")
            .email("sarah.johnson@hospital.com")
            .passwordHash("hashed_password_1")
            .role(UserRole.NURSE)
            .phone("555-0101")
            .build(),
        
        User.builder()
            .name("Michael Chen")
            .email("michael.chen@hospital.com")
            .passwordHash("hashed_password_2")
            .role(UserRole.NURSE)
            .phone("555-0102")
            .build(),
        
        User.builder()
            .name("Emily Rodriguez")
            .email("emily.rodriguez@hospital.com")
            .passwordHash("hashed_password_3")
            .role(UserRole.NURSE)
            .phone("555-0103")
            .build(),
        
        User.builder()
            .name("David Wilson")
            .email("david.wilson@hospital.com")
            .passwordHash("hashed_password_4")
            .role(UserRole.NURSE)
            .phone("555-0104")
            .build(),
        
        User.builder()
            .name("Lisa Thompson")
            .email("lisa.thompson@hospital.com")
            .passwordHash("hashed_password_5")
            .role(UserRole.NURSE)
            .phone("555-0105")
            .build(),
        
        User.builder()
            .name("Admin User")
            .email("admin@hospital.com")
            .passwordHash("hashed_admin_password")
            .role(UserRole.ADMIN)
            .phone("555-0001")
            .build(),
        
        User.builder()
            .name("Dr. James Miller")
            .email("james.miller@hospital.com")
            .passwordHash("hashed_doctor_password")
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
    // Create 5 nurses
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
    }
}
