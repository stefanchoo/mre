package com.mre.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@DiscriminatorValue("patient")
public class Patient extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1667826706406231262L;
	private String illness;                    // 疾患
	private String healthInsuranceNumber;      // 医保号码
	private Date beAdmissionTime;              // 入院时间
	// doctor 多对一的双向关系
	private Doctor doctor;
	// 一对多的关系
	private Set<RehabPlan> rehabPlans = new HashSet<RehabPlan>();
	private Set<PrescriptionUsed> myPrescriptions = new HashSet<PrescriptionUsed>();
	private Set<RehabAssessment> rehabAssessments = new HashSet<RehabAssessment>();
	private Set<RehabExperience> rehabExperiences = new HashSet<RehabExperience>();
	
	/**
	 * 获取身份信息
	 * @return
	 */
	@Transient
	public String identification() {
		return "病人 -- " + this.getTrueName();
	}
	
	@Column(length=100)
	public String getIllness() {
		return illness;
	}
	public void setIllness(String illness) {
		this.illness = illness;
	}
	@Column(length=60)
	public String getHealthInsuranceNumber() {
		return healthInsuranceNumber;
	}
	public void setHealthInsuranceNumber(String healthInsuranceNumber) {
		this.healthInsuranceNumber = healthInsuranceNumber;
	}
	@Type(type="timestamp")
	public Date getBeAdmissionTime() {
		return beAdmissionTime;
	}
	public void setBeAdmissionTime(Date beAdmissionTime) {
		this.beAdmissionTime = beAdmissionTime;
	}
	@ManyToOne
	@JoinColumn(name="doctorId")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	@OneToMany(mappedBy="patient")
	public Set<RehabPlan> getRehabPlans() {
		return rehabPlans;
	}
	public void setRehabPlans(Set<RehabPlan> rehabPlans) {
		this.rehabPlans = rehabPlans;
	}
	@OneToMany(mappedBy="patient")
	public Set<PrescriptionUsed> getMyPrescriptions() {
		return myPrescriptions;
	}
	public void setMyPrescriptions(Set<PrescriptionUsed> myPrescriptions) {
		this.myPrescriptions = myPrescriptions;
	}
	@OneToMany(mappedBy="patient")
	public Set<RehabAssessment> getRehabAssessments() {
		return rehabAssessments;
	}
	public void setRehabAssessments(Set<RehabAssessment> rehabAssessments) {
		this.rehabAssessments = rehabAssessments;
	}
	@OneToMany(mappedBy="author")
	public Set<RehabExperience> getRehabExperiences() {
		return rehabExperiences;
	}
	public void setRehabExperiences(Set<RehabExperience> rehabExperiences) {
		this.rehabExperiences = rehabExperiences;
	}
}
