package com.mre.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_prescription")
public class Prescription implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2089210750516548072L;
	private Long id;
	private String name;
	private String caseContent;                       // 病历内容
	private String prescriptionContent;               // 处方内容
	private String type;                              // 属于哪种类型
	private Date postTime;                            // 上传时间
	private Equipment equipment;                      // 针对于那个设备
	private Doctor author;                            // 上传的医生
	private Set<Doctor> collectors;                   // 收藏的医生
	
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
	@Column(length=30)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	@Type(type="timestamp")
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	@ManyToOne
	@JoinColumn(name="EquipmentId")
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	@ManyToOne
	@JoinColumn(name="authorId")
	public Doctor getAuthor() {
		return author;
	}
	public void setAuthor(Doctor author) {
		this.author = author;
	}
	
	@ManyToMany
	@JoinTable(name="prescription_collectors",
			joinColumns={
				@JoinColumn(name="prescriptionId", referencedColumnName="id")
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
}
