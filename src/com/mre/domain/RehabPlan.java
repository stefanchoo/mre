package com.mre.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_rehabplan")
public class RehabPlan implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5314681504628627550L;
	private Long id;
	private String name;
	private Date time;                      // 时间
	private int exerciseTime;               // 训练时长 单位：min
	private int actualExerciseTime;         // 实际训练的时长
	private String type;                    // 类型：运动治疗 物理治疗等    
	private String content;                 // 处方内容  大文本类型
	private String result;                  // 训练结果 治疗师反馈 提供简单的 一般 良好 优秀的选择
	private boolean completed = false;    	// 完成状态     
	
	// 多对一的关系
	private Doctor doctor;                  
	private Patient patient;
	private EquipmentUsed equipment;
	
	// 一对一的关系 
	private CentrobotRehabPlan centrobotRehabPlan;      // 与之对应的centrobot的康复计划
	
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
	public int getExerciseTime() {
		return exerciseTime;
	}
	public void setExerciseTime(int exerciseTime) {
		this.exerciseTime = exerciseTime;
	}
	@Column(length=100)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	// 大文本类型有很多种，指定大小让数据库自己选择
	@Type(type="text")
	@Column(length = 2000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getActualExerciseTime() {
		return actualExerciseTime;
	}
	public void setActualExerciseTime(int actualExerciseTime) {
		this.actualExerciseTime = actualExerciseTime;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@ManyToOne
	@JoinColumn(name="doctorId")
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
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
	@JoinColumn(name="equipmentId")
	public EquipmentUsed getEquipment() {
		return equipment;
	}
	
	public void setEquipment(EquipmentUsed equipment) {
		this.equipment = equipment;
	}
	
	@OneToOne(mappedBy="rehabPlan", cascade=CascadeType.REMOVE)
	public CentrobotRehabPlan getCentrobotRehabPlan() {
		return centrobotRehabPlan;
	}
	public void setCentrobotRehabPlan(CentrobotRehabPlan centrobotRehabPlan) {
		this.centrobotRehabPlan = centrobotRehabPlan;
	}
}
