package com.om.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.DocumentDao;
import com.om.domain.Document;
import com.om.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentDao docDao;
	
	@Override
	public Document createDocument(Document document) {
		return docDao.save(document);
	}

	@Override
	public List<Document> findAll() {
		return docDao.findAll();
	}

	@Override
	public Document findDocument(Long id) {
		return docDao.findById(id).orElse(null);
	}

	@Override
	public List<Document> findDocumentBycinPatient(String cinPatient) {
		return docDao.findDocumentByCinPatient(cinPatient);
	}

	@Override
	public List<Document> findDocumentByIdConsultation(String idConsul) {
		return docDao.findDocumentByidConsultation(idConsul);
	}

	@Override
	public List<Document> createAllDocuments(List<Document> docs) {
		List<Document> listDocsRetour = new ArrayList<>();
		if(docs != null && !docs.isEmpty()) {
			for(Document doc : docs) {
				listDocsRetour.add(docDao.save(doc));
			}
		}
		return listDocsRetour;
	}

	
}
