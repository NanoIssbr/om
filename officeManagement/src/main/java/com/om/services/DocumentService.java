package com.om.services;

import java.util.List;

import com.om.domain.Appointment;
import com.om.domain.Document;

public interface DocumentService {
	Document createDocument(Document document);
    List<Document> findAll();
    Document findDocument(Long id);
    List<Document> findDocumentBycinPatient(String cinPatient);
    List<Document> findDocumentByIdConsultation(String idConsul);
    List<Document> createAllDocuments(List<Document> docs);
}
