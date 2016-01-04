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

/**
 * 由管理员来管理，这里有大量的录入工作
 * @author Administrator
 *
 */
@Entity
@Table(name="_assessmentused")
public class AssessmentUsed implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4575464583930252632L;
	private Long id;
	private String name;
	private String type;
	private int totalScores;                          // 总分
	private String optionScores;                      // 每个选项对应的分数  格式： 40|30|20|10
	private String results;                           // 总分结果  格式： 30,----|60,----|80,----|100，---
	private int questionsCount;                       // 多少道题
	private Hospital hospital;                        // 隶属于哪家医院
	
	// 一对多的关系
	private Set<AssessmentContentUsed> assessmentContents = new HashSet<AssessmentContentUsed>();
	private Set<RehabAssessment> rehabAssessments = new HashSet<RehabAssessment>();
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
	public int getTotalScores() {
		return totalScores;
	}
	public void setTotalScores(int totalScores) {
		this.totalScores = totalScores;
	}
	@Column(length=60)
	public String getOptionScores() {
		return optionScores;
	}
	public void setOptionScores(String optionScores) {
		this.optionScores = optionScores;
	}
	@Type(type="text")
	@Column(length=1000)
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public int getQuestionsCount() {
		return questionsCount;
	}
	public void setQuestionsCount(int questionsCount) {
		this.questionsCount = questionsCount;
	}
	
	@ManyToOne
	@JoinColumn(name="hospitalId")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@OneToMany(mappedBy="assessmentUsed")
	public Set<AssessmentContentUsed> getAssessmentContents() {
		return assessmentContents;
	}
	public void setAssessmentContents(Set<AssessmentContentUsed> assessmentContents) {
		this.assessmentContents = assessmentContents;
	}
	@OneToMany(mappedBy="assessment")
	public Set<RehabAssessment> getRehabAssessments() {
		return rehabAssessments;
	}
	public void setRehabAssessments(Set<RehabAssessment> rehabAssessments) {
		this.rehabAssessments = rehabAssessments;
	}
}
