/**
 * 
 */
package com.om.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ibrayche
 *
 */
@Entity
public class Patient {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patientId", nullable = false, updatable = false)
	private Long patientId;
	
	@Column(name = "lastNamePatient", nullable = false, updatable = false)
	private String lastNamePatient;
	
	@Column(name = "firstNamePatient", nullable = false, updatable = false)
	private String firstNamePatient;
	
	private String agePatient;
	
	@Column(name = "cinPatient", nullable = false, updatable = false)
	private String cinPatient;
	
	@Column(name = "dateNaissancePatient", nullable = false, updatable = false)
	private Date dateNaissancePatient;
	
	@Column(name = "phonePatient", nullable = false, updatable = false)
	private String phonePatient;
	
	private String emailPatient;
	
	private String cinEncrypted;
	
	@Column(name = "adressePatient", nullable = false, updatable = false)
	private String adressePatient;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PatientMeasures> patientMeasure;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointmentList;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consultation> consultationList;
	
	@OneToOne
	private PatientMeasures pm;
	
	public PatientMeasures getPm() {
		return pm;
	}
	public void setPm(PatientMeasures pm) {
		this.pm = pm;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getLastNamePatient() {
		return lastNamePatient;
	}
	public void setLastNamePatient(String lastNamePatient) {
		this.lastNamePatient = lastNamePatient;
	}
	public String getFirstNamePatient() {
		return firstNamePatient;
	}
	public void setFirstNamePatient(String firstNamePatient) {
		this.firstNamePatient = firstNamePatient;
	}
	public String getAgePatient() {
		return agePatient;
	}
	public void setAgePatient(String agePatient) {
		this.agePatient = agePatient;
	}
	public String getCinPatient() {
		return cinPatient;
	}
	public void setCinPatient(String cinPatient) {
		this.cinPatient = cinPatient;
	}
	public List<PatientMeasures> getPatientMeasure() {
		return patientMeasure;
	}
	public void setPatientMeasure(List<PatientMeasures> patientMeasure) {
		this.patientMeasure = patientMeasure;
	}
	public Date getDateNaissancePatient() {
		return dateNaissancePatient;
	}
	public void setDateNaissancePatient(Date dateNaissancePatient) {
		this.dateNaissancePatient = dateNaissancePatient;
	}
	public String getPhonePatient() {
		return phonePatient;
	}
	public void setPhonePatient(String phonePatient) {
		this.phonePatient = phonePatient;
	}
	public String getEmailPatient() {
		return emailPatient;
	}
	public void setEmailPatient(String emailPatient) {
		this.emailPatient = emailPatient;
	}
	public String getAdressePatient() {
		return adressePatient;
	}
	public void setAdressePatient(String adressePatient) {
		this.adressePatient = adressePatient;
	}
	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}
	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}
	public String getCinEncrypted() {
		return cinEncrypted;
	}
	public void setCinEncrypted(String cinEncrypted) {
		this.cinEncrypted = cinEncrypted;
	}
	public Patient() {
		super();
	}
	public Patient(Long patientId, String lastNamePatient, String firstNamePatient, String agePatient,
			String cinPatient, Date dateNaissancePatient, String phonePatient, List<PatientMeasures> patientMeasure, String email, String adresse, List<Appointment> listAppointements) {
		super();
		this.patientId = patientId;
		this.lastNamePatient = lastNamePatient;
		this.firstNamePatient = firstNamePatient;
		this.agePatient = agePatient;
		this.cinPatient = cinPatient;
		this.dateNaissancePatient = dateNaissancePatient;
		this.phonePatient = phonePatient;
		this.patientMeasure = patientMeasure;
		this.emailPatient = email;
		this.adressePatient = adresse;
		this.appointmentList = listAppointements;
	}
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", lastNamePatient=" + lastNamePatient + ", firstNamePatient="
				+ firstNamePatient + ", agePatient=" + agePatient + ", cinPatient=" + cinPatient
				+ ", dateNaissancePatient=" + dateNaissancePatient + ", phonePatient=" + phonePatient
				+ ", patientMeasure=" + patientMeasure.toString() + "]";
	}
	
	
	
}
