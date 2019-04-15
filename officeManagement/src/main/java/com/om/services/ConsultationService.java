package com.om.services;

import java.util.List;

import com.om.domain.Consultation;

public interface ConsultationService {
	Consultation createConsultation(Consultation consul);
	List<Consultation> findAll();
	List<Consultation> findConsultationBycinPatient(String cin);
}
