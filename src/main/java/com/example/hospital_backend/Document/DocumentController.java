package com.example.hospital_backend.Document;

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
@RequestMapping("/api/Documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    private DocumentService DocumentService;

    // Get all Documents
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> Documents = DocumentService.getAllDocuments();
        return ResponseEntity.ok(Documents);
    }

    // Get Document by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> Document = DocumentService.getDocumentById(id);
        return Document.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Document
    @PostMapping("/create")
    public ResponseEntity<Document> createDocument(@RequestBody Document Document) {
        try {
            Document savedDocument = DocumentService.saveDocument(Document);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Document
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document Document) {
        Optional<Document> existingDocument = DocumentService.getDocumentById(id);
        if (existingDocument.isPresent()) {
            Document.setId(id); // Ensure the ID is set correctly
            Document updatedDocument = DocumentService.saveDocument(Document);
            return ResponseEntity.ok(updatedDocument);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        Optional<Document> existingDocument = DocumentService.getDocumentById(id);
        if (existingDocument.isPresent()) {
            DocumentService.deleteDocument(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
