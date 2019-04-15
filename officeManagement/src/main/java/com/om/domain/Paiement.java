/**
 * 
 */
package com.om.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ibrayche
 *
 */
@Entity
public class Paiement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paiementId", nullable = false, updatable = false)
	private Long paiementId;
	
	@ManyToOne
    @JoinColumn(name = "moyenPaiementId")
    @JsonIgnore
	private MoyenPaiement moyenPaiementObj;
	
	private double montant;
	private Date datePaiement;
	private String cinPatient;
	private String moyenPaiement;
	
	@Transient
	private Patient patient;
	
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getCinPatient() {
		return cinPatient;
	}
	public void setCinPatient(String cinPatient) {
		this.cinPatient = cinPatient;
	}
	public Long getPaiementId() {
		return paiementId;
	}
	public void setPaiementId(Long paiementId) {
		this.paiementId = paiementId;
	}
	public String getMoyenPaiement() {
		return moyenPaiement;
	}
	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}
	public MoyenPaiement getMoyenPaiementObj() {
		return moyenPaiementObj;
	}
	public void setMoyenPaiementObj(MoyenPaiement moyenPaiementObj) {
		this.moyenPaiementObj = moyenPaiementObj;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}
	
	public Paiement(Long paiementId, String moyenPaiement, MoyenPaiement moyenPaiementObj, double montant,
			Date datePaiement, String cin) {
		super();
		this.paiementId = paiementId;
		this.moyenPaiement = moyenPaiement;
		this.moyenPaiementObj = moyenPaiementObj;
		this.montant = montant;
		this.datePaiement = datePaiement;
		this.cinPatient = cin;
	}
	
	public Paiement() {
	}
	
	
	
	
}
