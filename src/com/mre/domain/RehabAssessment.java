package com.mre.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_rehabassessment")
public class RehabAssessment implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6237810041027735378L;
	private Long id;
	private Date time;                           // 时间
	private int score;
	private String result;
	private AssessmentUsed assessment;
	private Patient patient;
	private User operator;                        // 哪位帮忙做的评定，可以是自己，也可以是医师或者治疗师
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Type(type="timestamp")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@ManyToOne
	@JoinColumn(name="assessmentId")
	public AssessmentUsed getAssessment() {
		return assessment;
	}
	public void setAssessment(AssessmentUsed assessment) {
		this.assessment = assessment;
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
	@JoinColumn(name="operatorId")
	public User getOperator() {
		return operator;
	}
	public void setOperator(User operator) {
		this.operator = operator;
	}
}
