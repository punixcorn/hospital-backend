package com.example.hospital_backend.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Retrieve all {@link Client}s from the data store by status.
    // @param status the status to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByStatus(String status);

    // Retrieve all {@link Client}s from the data store by service type.
    // @param serviceType the service type to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByServiceType(String serviceType);

    // Retrieve all {@link Client}s from the data store by age.
    // @param age the age to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByAge(Integer age);

    // Retrieve all {@link Client}s from the data store by deceased.
    // @param deceased the deceased status to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByDeceased(Boolean deceased);

    // Find clients that have items with a specific name or description
    // @Query("SELECT DISTINCT c FROM Client c JOIN c.items i WHERE i.name LIKE %:itemName% OR i.description LIKE %:itemName%")
    // List<Client> findByItemsContaining(@Param("itemName") String itemName);

    // Find clients assigned to specific nurses (by nurse IDs)
    // @Query("SELECT DISTINCT c FROM Client c JOIN c.assignedNurses n WHERE n.id IN :nurseIds")
    // List<Client> findByAssignedNurseIds(@Param("nurseIds") List<Long> nurseIds);

    // Find clients with specific medications (by medication IDs)
    // @Query("SELECT DISTINCT c FROM Client c JOIN c.medication m WHERE m.id IN :medicationIds")
    // List<Client> findByMedicationIds(@Param("medicationIds") List<Long> medicationIds);

    // Retrieve all {@link Client}s from the data store by insurance.
    // @param insurance the insurance to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByInsurance(String insurance);

    // Find client by phone number (returns single client since phone is unique)
    Optional<Client> findByPhone(String phone);

    // Retrieve all {@link Client}s from the data store by condition.
    // @param condition the condition to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByCondition(String condition);

    // Retrieve all {@link Client}s from the data store by location.
    // @param location the location to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByLocation(String location);

    // Retrieve all {@link Client}s from the data store by name.
    // @param name the name to search for.
    // @return a Collection of {@link Client}s.
    List<Client> findByName(String name);

    // Find clients by exact date reachout
    List<Client> findByDateReachout(LocalDate dateReachout);

    // Find clients by exact date wanted
    List<Client> findByDateWanted(LocalDate dateWanted);

    // Additional useful queries you might want:
    
    // Find clients by name containing a substring (case insensitive)
    List<Client> findByNameContainingIgnoreCase(String name);
    
    // Find clients by location containing a substring
    List<Client> findByLocationContainingIgnoreCase(String location);
    
    // Find clients by condition containing a substring
    List<Client> findByConditionContainingIgnoreCase(String condition);

    List<Long> findAllMedicationIdsById(Long clientId);
}