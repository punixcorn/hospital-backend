package com.example.hospital_backend.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hospital_backend.Client.Client;
import com.example.hospital_backend.Client.ClientRepository;
import com.example.hospital_backend.Nurse.Nurse;
import com.example.hospital_backend.Nurse.NurseRepository;

@Service
@Transactional
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private NurseRepository nurseRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Get all notes with their basic information
     */
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll().stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get note by ID with full details
     */
    public Optional<NoteDTO> getNoteById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return noteRepository.findById(id)
            .flatMap(Note::toNoteDTO);
    }

    /**
     * Create a new note with validation
     */
    public NoteDTO createNote(CreateNoteRequest request) {
        // Validate required fields
        if (request.getNurseId() == null || request.getNurseId() <= 0) {
            throw new IllegalArgumentException("Valid nurse ID is required");
        }
        if (request.getClientId() == null || request.getClientId() <= 0) {
            throw new IllegalArgumentException("Valid client ID is required");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Note content is required");
        }
        
        // Validate nurse exists
        Nurse nurse = nurseRepository.findById(request.getNurseId())
            .orElseThrow(() -> new IllegalArgumentException("Nurse not found with ID: " + request.getNurseId()));
        
        // Validate client exists
        Client client = clientRepository.findById(request.getClientId())
            .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + request.getClientId()));
        
        // Set timestamp to current time if not provided
        LocalDateTime timestamp = request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now();
        
        // Build note entity
        Note note = Note.builder()
            .nurse(nurse)
            .client(client)
            .timestamp(timestamp)
            .content(request.getContent().trim())
            .build();
        
        Note savedNote = noteRepository.save(note);
        return savedNote.toNoteDTO().orElseThrow(() -> new RuntimeException("Failed to convert note to DTO"));
    }

    /**
     * Update an existing note
     */
    public NoteDTO updateNote(Long id, UpdateNoteRequest request) {
        Note existingNote = noteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Note not found with ID: " + id));
        
        // Update fields if provided
        if (request.getTimestamp() != null) {
            existingNote.setTimestamp(request.getTimestamp());
        }
        if (request.getContent() != null && !request.getContent().trim().isEmpty()) {
            existingNote.setContent(request.getContent().trim());
        } else if (request.getContent() != null && request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Note content cannot be empty");
        }
        
        Note updatedNote = noteRepository.save(existingNote);
        return updatedNote.toNoteDTO().orElseThrow(() -> new RuntimeException("Failed to convert note to DTO"));
    }

    /**
     * Delete a note
     */
    public void deleteNote(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid note ID");
        }
        
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note not found with ID: " + id);
        }
        
        noteRepository.deleteById(id);
    }

    /**
     * Get notes by nurse ID
     */
    public List<NoteDTO> getNotesByNurseId(Long nurseId) {
        List<Note> notes = noteRepository.findByNurseId(nurseId);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes by client ID
     */
    public List<NoteDTO> getNotesByClientId(Long clientId) {
        List<Note> notes = noteRepository.findByClientId(clientId);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes by nurse and client IDs
     */
    public List<NoteDTO> getNotesByNurseAndClient(Long nurseId, Long clientId) {
        List<Note> notes = noteRepository.findByNurseIdAndClientId(nurseId, clientId);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes created after a specific timestamp
     */
    public List<NoteDTO> getNotesCreatedAfter(LocalDateTime timestamp) {
        List<Note> notes = noteRepository.findByCreatedAtAfter(timestamp);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes created before a specific timestamp
     */
    public List<NoteDTO> getNotesCreatedBefore(LocalDateTime timestamp) {
        List<Note> notes = noteRepository.findByCreatedAtBefore(timestamp);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes created between two timestamps
     */
    public List<NoteDTO> getNotesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        List<Note> notes = noteRepository.findByCreatedAtBetween(startTime, endTime);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get notes by timestamp range
     */
    public List<NoteDTO> getNotesByTimestampRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<Note> notes = noteRepository.findByTimestampBetween(startTime, endTime);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Search notes by content containing substring (case insensitive)
     */
    public List<NoteDTO> searchNotesByContent(String content) {
        List<Note> notes = noteRepository.findByContentContainingIgnoreCase(content);
        return notes.stream()
            .map(note -> note.toNoteDTO().orElse(null))
            .filter(dto -> dto != null)
            .toList();
    }

    /**
     * Get the most recent note for a client
     */
    public Optional<NoteDTO> getMostRecentNoteForClient(Long clientId) {
        return noteRepository.findMostRecentNoteForClient(clientId)
            .flatMap(Note::toNoteDTO);
    }

    /**
     * Get the most recent note by a nurse
     */
    public Optional<NoteDTO> getMostRecentNoteByNurse(Long nurseId) {
        return noteRepository.findMostRecentNoteByNurse(nurseId)
            .flatMap(Note::toNoteDTO);
    }

    /**
     * Count notes by nurse
     */
    public long countNotesByNurse(Long nurseId) {
        return noteRepository.countByNurseId(nurseId);
    }

    /**
     * Count notes by client
     */
    public long countNotesByClient(Long clientId) {
        return noteRepository.countByClientId(clientId);
    }

    /**
     * Count notes by nurse and client
     */
    public long countNotesByNurseAndClient(Long nurseId, Long clientId) {
        return noteRepository.countByNurseIdAndClientId(nurseId, clientId);
    }
}
