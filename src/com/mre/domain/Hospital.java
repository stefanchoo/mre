package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_hospital")
public class Hospital implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3841180986549192770L;
	private Long id;
	private String name;
	private String icon;
	private String description;
	private String city;                          				// 隶属于那座城市
	private String href;                          				// 超链接地址
	private String level;                         				// 级别
	private Set<User> users = new HashSet<User>();              // 医院的医生  患者
	// 多对多关系
	private Set<Equipment> equipments = new HashSet<Equipment>();
	// 一对多的 关系
	private Set<EquipmentUsed> equipmentUseds = new HashSet<EquipmentUsed>();
	
	// 医院的康复量表 一对多关系
	private Set<AssessmentUsed> assessments = new HashSet<AssessmentUsed>();
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Type(type="text")
	@Column(length=2000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(length=20)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(length=60)
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	@Column(length=20)
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@OneToMany(mappedBy="hospital")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@ManyToMany(mappedBy="hospitals")
	public Set<Equipment> getEquipments() {
		return equipments;
	}
	public void setEquipments(Set<Equipment> equipments) {
		this.equipments = equipments;
	}
	@OneToMany(mappedBy="hospital")
	public Set<AssessmentUsed> getAssessments() {
		return assessments;
	}
	public void setAssessments(Set<AssessmentUsed> assessments) {
		this.assessments = assessments;
	}
	@OneToMany(mappedBy="hospital")
	public Set<EquipmentUsed> getEquipmentUseds() {
		return equipmentUseds;
	}
	public void setEquipmentUseds(Set<EquipmentUsed> equipmentUseds) {
		this.equipmentUseds = equipmentUseds;
	}	
}
