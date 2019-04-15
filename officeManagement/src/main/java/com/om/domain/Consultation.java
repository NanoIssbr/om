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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * @author ibrayche
 *
 */
@Entity
public class Consultation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@OneToOne
	private Paiement paiement;
	
	@Column(name = "dateConsultation", nullable = false, updatable = false)
	private Date dateConsultation;
	
	private boolean isControle;
	private String description;
	
	@Column(name = "cinPatient", nullable = false, updatable = false)
	private String cinPatient;
	
	private String docs;
	
	@Transient
	private List<String> listDocs;
	
	@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Document> listDocuments;
	
	@Transient
	private List<MoyenPaiement> moyensPaiements;
	
	
	public List<MoyenPaiement> getMoyensPaiements() {
		return moyensPaiements;
	}

	public void setMoyensPaiements(List<MoyenPaiement> moyensPaiements) {
		this.moyensPaiements = moyensPaiements;
	}

	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}

	public List<String> getListDocs() {
		return listDocs;
	}

	public void setListDocs(List<String> listDocs) {
		this.listDocs = listDocs;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateConsultation() {
		return dateConsultation;
	}

	public void setDateConsultation(Date dateConsultation) {
		this.dateConsultation = dateConsultation;
	}

	public boolean getIsControle() {
		return isControle;
	}

	public void setIsControle(boolean isControle) {
		this.isControle = isControle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getCinPatient() {
		return cinPatient;
	}

	public void setCinPatient(String cinPatient) {
		this.cinPatient = cinPatient;
	}

	public Consultation() {
		Paiement pai = new Paiement();
		this.setPaiement(pai);
	}
	

	public List<Document> getListDocuments() {
		return listDocuments;
	}

	public void setListDocuments(List<Document> listDocuments) {
		this.listDocuments = listDocuments;
	}

	public Consultation(long id, Patient patient, Date dateConsultation, boolean isControle, String description,String cinPatient, String docs) {
		super();
		this.id = id;
		this.patient = patient;
		this.dateConsultation = dateConsultation;
		this.isControle = isControle;
		this.description = description;
		this.cinPatient = cinPatient;
		this.docs = docs;
	}

}
