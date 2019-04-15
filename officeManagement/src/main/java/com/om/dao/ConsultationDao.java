package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.Consultation;

public interface ConsultationDao extends CrudRepository<Consultation, Long> {
	List<Consultation> findAll();
	List<Consultation> findConsultationBycinPatient(String cin);
}
