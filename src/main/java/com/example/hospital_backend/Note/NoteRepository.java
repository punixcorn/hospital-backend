package com.example.hospital_backend.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    /**
     * Find notes by nurse ID
     */
    List<Note> findByNurseId(Long nurseId);
    
    /**
     * Find notes by client ID
     */
    List<Note> findByClientId(Long clientId);
    
    /**
     * Find notes by nurse and client IDs
     */
    List<Note> findByNurseIdAndClientId(Long nurseId, Long clientId);
    
    /**
     * Find notes created after a specific timestamp
     */
    List<Note> findByCreatedAtAfter(LocalDateTime timestamp);
    
    /**
     * Find notes created before a specific timestamp
     */
    List<Note> findByCreatedAtBefore(LocalDateTime timestamp);
    
    /**
     * Find notes created between two timestamps
     */
    List<Note> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find notes by timestamp (when the note was actually written)
     */
    List<Note> findByTimestamp(LocalDateTime timestamp);
    
    /**
     * Find notes by timestamp range
     */
    List<Note> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find notes containing specific text in content (case insensitive)
     */
    List<Note> findByContentContainingIgnoreCase(String content);
    
    /**
     * Find notes by content starting with specific text (case insensitive)
     */
    List<Note> findByContentStartingWithIgnoreCase(String content);
    
    /**
     * Find notes by content ending with specific text (case insensitive)
     */
    List<Note> findByContentEndingWithIgnoreCase(String content);
    
    /**
     * Find notes for a specific client created by a specific nurse
     */
    @Query("SELECT n FROM Note n WHERE n.client.id = :clientId AND n.nurse.id = :nurseId ORDER BY n.createdAt DESC")
    List<Note> findNotesForClientByNurse(@Param("clientId") Long clientId, @Param("nurseId") Long nurseId);
    
    /**
     * Find the most recent note for a client
     */
    @Query("SELECT n FROM Note n WHERE n.client.id = :clientId ORDER BY n.createdAt DESC LIMIT 1")
    Optional<Note> findMostRecentNoteForClient(@Param("clientId") Long clientId);
    
    /**
     * Find the most recent note by a nurse
     */
    @Query("SELECT n FROM Note n WHERE n.nurse.id = :nurseId ORDER BY n.createdAt DESC LIMIT 1")
    Optional<Note> findMostRecentNoteByNurse(@Param("nurseId") Long nurseId);
    
    /**
     * Count notes by nurse
     */
    long countByNurseId(Long nurseId);
    
    /**
     * Count notes by client
     */
    long countByClientId(Long clientId);
    
    /**
     * Count notes by nurse and client
     */
    long countByNurseIdAndClientId(Long nurseId, Long clientId);
}
