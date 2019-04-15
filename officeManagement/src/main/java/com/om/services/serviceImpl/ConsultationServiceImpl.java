package com.om.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.ConsultationDao;
import com.om.domain.Consultation;
import com.om.services.ConsultationService;

@Service
public class ConsultationServiceImpl implements ConsultationService {

	@Autowired
	private ConsultationDao consultationDao;

	@Override
	public Consultation createConsultation(Consultation consul) {
		return consultationDao.save(consul);
	}

	@Override
	public List<Consultation> findAll() {
		return consultationDao.findAll();
	}

	@Override
	public List<Consultation> findConsultationBycinPatient(String cin) {
		return consultationDao.findConsultationBycinPatient(cin);
	}
	
	

}
