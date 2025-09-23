package com.example.hospital_backend.Document;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository DocumentRepository;

    public Optional<Document> getDocumentById(Long id) {
        return DocumentRepository.findById(id);
    }

    public List<Document> getAllDocuments() {
        return DocumentRepository.findAll();
    }

    public Document saveDocument(Document Document) {
        return DocumentRepository.save(Document);
    }

    public void deleteDocument(Long id) {
        DocumentRepository.deleteById(id);
    }

}
