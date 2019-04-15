package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.Document;

public interface DocumentDao extends CrudRepository<Document, Long> {

    List<Document> findAll();
    List<Document> findDocumentByCinPatient(String cinPatient);
    List<Document> findDocumentByidConsultation(String idConsultation);
}
