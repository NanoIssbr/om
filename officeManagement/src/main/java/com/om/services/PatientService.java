package com.om.services;

import java.util.List;

import com.om.domain.Patient;

public interface PatientService {
	Patient createPatient(Patient patient);
	List<Patient> findAll();
    Patient findPatient(Long id);
    Patient findPatientBycinPatient(String cin);
    Boolean checkIfCINEwist(String cin);
}
