package com.om.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.om.domain.Patient;

public interface PatientDao extends CrudRepository<Patient, Long> {
	List<Patient> findAll();
	Patient findPatientBycinPatient(String cin);
}
