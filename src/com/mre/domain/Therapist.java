package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("therapist")
public class Therapist extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725211986564481786L;
	// 一对多的关系
	private Set<EquipmentUsed> equipments = new HashSet<EquipmentUsed>();
	
	/**
	 * 获取身份信息
	 * @return
	 */
	@Transient
	public String identification() {
		return "治疗师 -- " + this.getTrueName();
	}
	
	// 取出治疗师负责的设备
	@OneToMany(mappedBy="therapist", fetch=FetchType.EAGER)
	public Set<EquipmentUsed> getEquipments() {
		return equipments;
	}

	public void setEquipments(Set<EquipmentUsed> equipments) {
		this.equipments = equipments;
	}
}
