package com.example.hospital_backend.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Note management
 * 
 * Provides endpoints for:
 * - Creating, reading, updating, and deleting notes
 * - Searching notes by various criteria (nurse, client, content, dates)
 * - Managing note content and timestamps
 * 
 * Access: ADMIN and NURSE roles
 */
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
@Validated
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Get all notes
     * 
     * @return List of all notes with their basic information
     */
    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    /**
     * Get note by ID
     * 
     * @param id Note ID (must be positive)
     * @return Note details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id)
            .map(note -> ResponseEntity.ok(note))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new note
     * 
     * Required fields:
     * - nurseId: ID of the nurse creating the note (must reference existing nurse)
     * - clientId: ID of the client the note is about (must reference existing client)
     * - content: The note content (cannot be empty)
     * 
     * Optional fields:
     * - timestamp: When the note was created (defaults to current time)
     * 
     * @param request CreateNoteRequest with note details
     * @return Created note or error message
     */
    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest request) {
        try {
            NoteDTO createdNote = noteService.createNote(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create note: " + e.getMessage()));
        }
    }

    /**
     * Update an existing note
     * 
     * @param id Note ID (must be positive)
     * @param request UpdateNoteRequest with fields to update
     * @return Updated note or error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(
            @PathVariable Long id, 
            @RequestBody UpdateNoteRequest request) {
        try {
            NoteDTO updatedNote = noteService.updateNote(id, request);
            return ResponseEntity.ok(updatedNote);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to update note: " + e.getMessage()));
        }
    }

    /**
     * Delete a note
     * 
     * @param id Note ID (must be positive)
     * @return 204 No Content on success, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete note: " + e.getMessage()));
        }
    }

    /**
     * Get notes by nurse ID
     * 
     * @param nurseId Nurse ID
     * @return List of notes created by the specified nurse
     */
    @GetMapping("/by-nurse/{nurseId}")
    public ResponseEntity<List<NoteDTO>> getNotesByNurseId(@PathVariable Long nurseId) {
        List<NoteDTO> notes = noteService.getNotesByNurseId(nurseId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes by client ID
     * 
     * @param clientId Client ID
     * @return List of notes about the specified client
     */
    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<NoteDTO>> getNotesByClientId(@PathVariable Long clientId) {
        List<NoteDTO> notes = noteService.getNotesByClientId(clientId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes by nurse and client IDs
     * 
     * @param nurseId Nurse ID
     * @param clientId Client ID
     * @return List of notes created by the nurse about the client
     */
    @GetMapping("/by-nurse/{nurseId}/client/{clientId}")
    public ResponseEntity<List<NoteDTO>> getNotesByNurseAndClient(
            @PathVariable Long nurseId, 
            @PathVariable Long clientId) {
        List<NoteDTO> notes = noteService.getNotesByNurseAndClient(nurseId, clientId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes created after a specific timestamp
     * 
     * @param timestamp Timestamp to search after
     * @return List of notes created after the specified timestamp
     */
    @GetMapping("/created-after")
    public ResponseEntity<List<NoteDTO>> getNotesCreatedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
        List<NoteDTO> notes = noteService.getNotesCreatedAfter(timestamp);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes created before a specific timestamp
     * 
     * @param timestamp Timestamp to search before
     * @return List of notes created before the specified timestamp
     */
    @GetMapping("/created-before")
    public ResponseEntity<List<NoteDTO>> getNotesCreatedBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp) {
        List<NoteDTO> notes = noteService.getNotesCreatedBefore(timestamp);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes created between two timestamps
     * 
     * @param startTime Start timestamp
     * @param endTime End timestamp
     * @return List of notes created between the specified timestamps
     */
    @GetMapping("/created-between")
    public ResponseEntity<List<NoteDTO>> getNotesCreatedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<NoteDTO> notes = noteService.getNotesCreatedBetween(startTime, endTime);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get notes by timestamp range (when the note was actually written)
     * 
     * @param startTime Start timestamp
     * @param endTime End timestamp
     * @return List of notes written between the specified timestamps
     */
    @GetMapping("/timestamp-between")
    public ResponseEntity<List<NoteDTO>> getNotesByTimestampRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<NoteDTO> notes = noteService.getNotesByTimestampRange(startTime, endTime);
        return ResponseEntity.ok(notes);
    }

    /**
     * Search notes by content containing substring (case insensitive)
     * 
     * @param content Content substring to search for
     * @return List of notes containing the specified content
     */
    @GetMapping("/search")
    public ResponseEntity<List<NoteDTO>> searchNotesByContent(@RequestParam String content) {
        List<NoteDTO> notes = noteService.searchNotesByContent(content);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get the most recent note for a client
     * 
     * @param clientId Client ID
     * @return Most recent note for the client or 404 if not found
     */
    @GetMapping("/most-recent/client/{clientId}")
    public ResponseEntity<?> getMostRecentNoteForClient(@PathVariable Long clientId) {
        return noteService.getMostRecentNoteForClient(clientId)
            .map(note -> ResponseEntity.ok(note))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get the most recent note by a nurse
     * 
     * @param nurseId Nurse ID
     * @return Most recent note by the nurse or 404 if not found
     */
    @GetMapping("/most-recent/nurse/{nurseId}")
    public ResponseEntity<?> getMostRecentNoteByNurse(@PathVariable Long nurseId) {
        return noteService.getMostRecentNoteByNurse(nurseId)
            .map(note -> ResponseEntity.ok(note))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Count notes by nurse
     * 
     * @param nurseId Nurse ID
     * @return Count of notes created by the nurse
     */
    @GetMapping("/count/nurse/{nurseId}")
    public ResponseEntity<Map<String, Object>> countNotesByNurse(@PathVariable Long nurseId) {
        long count = noteService.countNotesByNurse(nurseId);
        return ResponseEntity.ok(Map.of("nurseId", nurseId, "count", count));
    }

    /**
     * Count notes by client
     * 
     * @param clientId Client ID
     * @return Count of notes about the client
     */
    @GetMapping("/count/client/{clientId}")
    public ResponseEntity<Map<String, Object>> countNotesByClient(@PathVariable Long clientId) {
        long count = noteService.countNotesByClient(clientId);
        return ResponseEntity.ok(Map.of("clientId", clientId, "count", count));
    }

    /**
     * Count notes by nurse and client
     * 
     * @param nurseId Nurse ID
     * @param clientId Client ID
     * @return Count of notes created by the nurse about the client
     */
    @GetMapping("/count/nurse/{nurseId}/client/{clientId}")
    public ResponseEntity<Map<String, Object>> countNotesByNurseAndClient(
            @PathVariable Long nurseId, 
            @PathVariable Long clientId) {
        long count = noteService.countNotesByNurseAndClient(nurseId, clientId);
        return ResponseEntity.ok(Map.of("nurseId", nurseId, "clientId", clientId, "count", count));
    }
}
