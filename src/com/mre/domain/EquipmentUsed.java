package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_equipmentused")
public class EquipmentUsed implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6252353632989531713L;
	private Long id;
	private String name;
	private String number;                  //用来是指示那款设备
	private String type;
	private String description;
	private int status;
	
	// 一对多的关系
	private Set<RehabPlan> rehabPlans = new HashSet<RehabPlan>();
	// 多对一的关系
	private Therapist therapist;
	
	// 多对一的关系
	private Hospital hospital;
	
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
	@Column(length=60)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Column(length=30)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Type(type="text")
	@Column(length=2000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(mappedBy="equipment")
	public Set<RehabPlan> getRehabPlans() {
		return rehabPlans;
	}
	public void setRehabPlans(Set<RehabPlan> rehabPlans) {
		this.rehabPlans = rehabPlans;
	}
	
	@ManyToOne
	@JoinColumn(name="therapistId")
	public Therapist getTherapist() {
		return therapist;
	}
	public void setTherapist(Therapist therapist) {
		this.therapist = therapist;
	}
	
	@ManyToOne
	@JoinColumn(name="hospitalId")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}
