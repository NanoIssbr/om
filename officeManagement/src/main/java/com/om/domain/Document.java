/**
 * 
 */
package com.om.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author ibrayche
 *
 */
@Entity
public class Document {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String urlDoc;
	private String displayName;
	private String cinPatient;
	private String idConsultation;
	
	@ManyToOne
    @JoinColumn(name = "consul_id")
	private Consultation consultation;
	
	public String getCinPatient() {
		return cinPatient;
	}
	public void setCinPatient(String cinPatient) {
		this.cinPatient = cinPatient;
	}
	public String getIdConsultation() {
		return idConsultation;
	}
	public void setIdConsultation(String idConsultation) {
		this.idConsultation = idConsultation;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrlDoc() {
		return urlDoc;
	}
	public void setUrlDoc(String urlDoc) {
		this.urlDoc = urlDoc;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
	public Document(long id, String urlDoc, String displayName, String cinPatient, String idConsultation) {
		super();
		this.id = id;
		this.urlDoc = urlDoc;
		this.displayName = displayName;
		this.cinPatient = cinPatient;
		this.idConsultation = idConsultation;
	}
	public Document() {
		
	}
	
	
}
