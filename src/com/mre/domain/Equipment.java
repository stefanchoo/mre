package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_equipment")
public class Equipment implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3411949610079646193L;
	private Long id;
	private String name;
	private String icon;                                                          // 图片
	private String type;                                                          // 类型  物理治疗设备 作业治疗设备等~
	private String description;
	private String indiction;                                                     // 适应症
	private String manufacturers;                                                 // 制造商
	private Set<Hospital> hospitals = new HashSet<Hospital>();                    // 拥有医院
	private Set<Prescription> prescriptions = new HashSet<Prescription>();        // 处方
	private Set<Equipment> children = new HashSet<Equipment>();                   // 树状结构的孩子
	private Equipment parent;                                                     // 树状结构的父亲
	private Set<Doctor> collectors = new HashSet<Doctor>();                 	  // 收藏该设备的医生
	private boolean isRecommended = false;                                        // 是否是推荐的设备
	
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
	@Type(type="text")
	@Column(length=2000)
	public String getIndiction() {
		return indiction;
	}
	public void setIndiction(String indiction) {
		this.indiction = indiction;
	}
	@Type(type="text")
	@Column(length=2000)
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}
	// 多对多的关系 由equipment表来维护中间表
	@ManyToMany
	@JoinTable(name="equipment_hospital",
			joinColumns={
				@JoinColumn(name="equipmentId", referencedColumnName="id")
			},
			inverseJoinColumns={
				@JoinColumn(name="hospitalId", referencedColumnName="id")
	})
	public Set<Hospital> getHospitals() {
		return hospitals;
	}
	public void setHospitals(Set<Hospital> hospitals) {
		this.hospitals = hospitals;
	}
	// 一对多的关系
	@OneToMany(mappedBy="equipment")
	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	//一对多的关系
	@OneToMany(mappedBy="parent")
	public Set<Equipment> getChildren() {
		return children;
	}
	public void setChildren(Set<Equipment> children) {
		this.children = children;
	}
	//多对一的关系
	@ManyToOne
	@JoinColumn(name="parentId")
	public Equipment getParent() {
		return parent;
	}
	public void setParent(Equipment parent) {
		this.parent = parent;
	}
	//多对多的关系
	@ManyToMany
	@JoinTable(name="equipment_collectors",
			joinColumns={
				@JoinColumn(name="equipmentId", referencedColumnName="id")
			},
			inverseJoinColumns={
				@JoinColumn(name="doctorId", referencedColumnName="id")
	})
	public Set<Doctor> getCollectors() {
		return collectors;
	}
	public void setCollectors(Set<Doctor> collectors) {
		this.collectors = collectors;
	}
	public boolean isRecommended() {
		return isRecommended;
	}
	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}
}
