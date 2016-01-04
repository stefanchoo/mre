package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@DiscriminatorValue("doctor")
public class Doctor extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615684568257698579L;
	private String department;           // 部门
	private String title;                // 职称
	private String goodAt;               // 擅长
	private boolean expert = false;    // 专家 
	
	// 一对多的双向关系
	private Set<Patient> patients = new HashSet<Patient>();
	// 一对多的双向关系
	private Set<RehabPlan> rehabPlans = new HashSet<RehabPlan>();
	// 收藏设备的医生 多对多的关系
	private Set<Equipment> equipments = new HashSet<Equipment>();
	
	// 收藏处方的医生  多对多的关系
	private Set<Prescription> prescriptionsCollected = new HashSet<Prescription>();
	
	// 上传处方的医生 一对多的关系
	private Set<Prescription> prescriptionsUploaded = new HashSet<Prescription>();
	
	// 为患者指定的处方 一对多的关系 
	private Set<PrescriptionUsed> myPrescriptions = new HashSet<PrescriptionUsed>(); 
	
	/**
	 * 获取身份信息
	 * @return
	 */
	@Transient
	public String identification() {
		return "医生 -- " + this.getTrueName();
	}
	
	@OneToMany(mappedBy="doctor")
	public Set<Patient> getPatients() {
		return patients;
	}
	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	@OneToMany(mappedBy="doctor")
	public Set<RehabPlan> getRehabPlans() {
		return rehabPlans;
	}
	public void setRehabPlans(Set<RehabPlan> rehabPlans) {
		this.rehabPlans = rehabPlans;
	}
	@ManyToMany(mappedBy="collectors")
	public Set<Equipment> getEquipments() {
		return equipments;
	}
	public void setEquipments(Set<Equipment> equipments) {
		this.equipments = equipments;
	}
	@ManyToMany(mappedBy="collectors")
	public Set<Prescription> getPrescriptionsCollected() {
		return prescriptionsCollected;
	}
	public void setPrescriptionsCollected(Set<Prescription> prescriptionsCollected) {
		this.prescriptionsCollected = prescriptionsCollected;
	}
	@OneToMany(mappedBy="author")
	public Set<Prescription> getPrescriptionsUploaded() {
		return prescriptionsUploaded;
	}
	public void setPrescriptionsUploaded(Set<Prescription> prescriptionsUploaded) {
		this.prescriptionsUploaded = prescriptionsUploaded;
	}
	@OneToMany(mappedBy="doctor")
	public Set<PrescriptionUsed> getMyPrescriptions() {
		return myPrescriptions;
	}
	public void setMyPrescriptions(Set<PrescriptionUsed> myPrescriptions) {
		this.myPrescriptions = myPrescriptions;
	}
	@Column(length=30)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Column(length=30)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Type(type="text")
	@Column(length = 2000)
	public String getGoodAt() {
		return goodAt;
	}
	public void setGoodAt(String goodAt) {
		this.goodAt = goodAt;
	}
	public boolean isExpert() {
		return expert;
	}
	public void setExpert(boolean expert) {
		this.expert = expert;
	}
}
