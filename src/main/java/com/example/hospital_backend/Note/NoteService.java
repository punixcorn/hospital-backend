package com.example.hospital_backend.Note;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepository NoteRepository;

    public Optional<Note> getNoteById(Long id) {
        return NoteRepository.findById(id);
    }

    public List<Note> getAllNotes() {
        return NoteRepository.findAll();
    }

    public Note saveNote(Note Note) {
        return NoteRepository.save(Note);
    }

    public void deleteNote(Long id) {
        NoteRepository.deleteById(id);
    }

}
