package com.om.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.om.dao.PatientDao;
import com.om.domain.Patient;
import com.om.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientDao patientDao;
	
	@Override
	public Patient createPatient(Patient patient) {
		return patientDao.save(patient);
	}

	@Override
	public List<Patient> findAll() {
		return patientDao.findAll();
	}

	@Override
	public Patient findPatient(Long id) {
		return patientDao.findById(id).orElse(null);
	}

	@Override
	public Patient findPatientBycinPatient(String cin) {
		return patientDao.findPatientBycinPatient(cin);
	}
	
	public Boolean checkIfCINEwist(String cin) {
		Boolean exist = false;
		if(patientDao.findPatientBycinPatient(cin) != null) {
			exist = true;
		}
		return exist;
	}

}
