package com.example.hospital_backend.Note;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Notes")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    private NoteService NoteService;

    // Get all Notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> Notes = NoteService.getAllNotes();
        return ResponseEntity.ok(Notes);
    }

    // Get Note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> Note = NoteService.getNoteById(id);
        return Note.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Note
    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody Note Note) {
        try {
            Note savedNote = NoteService.saveNote(Note);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note Note) {
        Optional<Note> existingNote = NoteService.getNoteById(id);
        if (existingNote.isPresent()) {
            Note.setId(id); // Ensure the ID is set correctly
            Note updatedNote = NoteService.saveNote(Note);
            return ResponseEntity.ok(updatedNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        Optional<Note> existingNote = NoteService.getNoteById(id);
        if (existingNote.isPresent()) {
            NoteService.deleteNote(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
