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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ibrayche
 *
 */
@Entity
public class PatientMeasures implements Comparable<PatientMeasures> {
	
	@ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
	private Patient patient;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "measureId", nullable = false, updatable = false)
	private Long measureId;
	
	@Column(name = "length", nullable = false, unique = false)
	private String length;
	
	@Column(name = "weight", nullable = false, unique = false)
	private Double weight;
	
	@Column(name = "dateMeasure", nullable = false, unique = false)
	private Date dateMeasure;
	
	
	public Date getDateMeasure() {
		return dateMeasure;
	}
	public void setDateMeasure(Date dateMeasure) {
		this.dateMeasure = dateMeasure;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public PatientMeasures() {
		
	}
	public PatientMeasures(String length, Double weight, Date dateMeasure) {
		super();
		this.dateMeasure = dateMeasure;
		this.length = length;
		this.weight = weight;
	}
	@Override
	public int compareTo(PatientMeasures o) {
		if(getDateMeasure() == null || o.getDateMeasure() == null) {
			return 0;
		}
		return getDateMeasure().compareTo(o.getDateMeasure());
	}
	
}
