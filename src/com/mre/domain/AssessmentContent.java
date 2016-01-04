package com.mre.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="_assessmentcontent")
public class AssessmentContent implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 49153112037575683L;
	private Long id;
	private String question;                // 问题名称 
	private String answers;                 // 答案列表  使用A.dddd|B.dddd|C.dddd|D.ddsddd的格式存入，取出后split('|')组成答案数组
	private Assessment assessment;          // 隶属于哪张表
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Type(type="text")
	@Column(length=1000)
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	@ManyToOne
	@JoinColumn(name="assessmentId")
	public Assessment getAssessment() {
		return assessment;
	}
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
}
