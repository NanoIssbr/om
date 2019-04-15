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

/**
 * @author ibrayche
 *
 */
@Entity
public class MoyenPaiement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paiementId", nullable = false, updatable = false)
	private Long idMoyenPaiement;
	
	@Column(name = "moyenPaiement", nullable = false, updatable = false, unique = true)
	private String moyenPaiement;
	private Date dateMP;
	
	public Long getIdMoyenPaiement() {
		return idMoyenPaiement;
	}
	public void setIdMoyenPaiement(Long idMoyenPaiement) {
		this.idMoyenPaiement = idMoyenPaiement;
	}
	public String getMoyenPaiement() {
		return moyenPaiement;
	}
	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}
	
	
	public Date getDateMP() {
		return dateMP;
	}
	public void setDateMP(Date dateMP) {
		this.dateMP = dateMP;
	}
	public MoyenPaiement( String moyenPaiement, Date dateMP) {
		super();
		this.moyenPaiement = moyenPaiement;
		this.dateMP = dateMP;
	}
	public MoyenPaiement() {
	}

}
