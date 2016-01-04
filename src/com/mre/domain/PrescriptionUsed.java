package com.mre.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_prescriptionused")
public class PrescriptionUsed implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5189598985370468011L;
	private Long id;
	private String name;
	private String caseContent;
	private String prescriptionContent;
	private Date time;                       // 处方时间
	private Patient patient;
	private Doctor doctor;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length=60)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Type(type="timestamp")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Type(type="text")
	@Column(length=4000)
	public String getCaseContent() {
		return caseContent;
	}
	public void setCaseContent(String caseContent) {
		this.caseContent = caseContent;
	}
	@Type(type="text")
	@Column(length=65535)
	public String getPrescriptionContent() {
		return prescriptionContent;
	}
	public void setPrescriptionContent(String prescriptionContent) {
		this.prescriptionContent = prescriptionContent;
	}
	@ManyToOne
	@JoinColumn(name="patientId")
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	@ManyToOne
	@JoinColumn(name="doctorId")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
