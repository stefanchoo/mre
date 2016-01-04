package com.mre.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 由管理员来管理，这里有大量的录入工作
 * @author Administrator
 *
 */
@Entity
@Table(name="_assessment")
public class Assessment implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8421220038987828887L;
	private Long id;
	private String name;
	private String type;
	private int totalScores;                       // 总分
	private String scores;                         // 每个选项对应的分数  格式： 40|30|20|10
	private String results;                        // 总分结果  格式： 30,----|60,----|80,----|100，---
	private int questionsCount;                    // 多少道题
	private Long visitorsCount;                    // 浏览量 （显示主页时的排序）
	private Long downloadCount;                    // 下载量
	
	// 一对多的关系
	private Set<AssessmentContent> assessmentContents = new HashSet<AssessmentContent>();
	
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
	public String getScores() {
		return scores;
	}
	public void setScores(String scores) {
		this.scores = scores;
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
	public Long getVisitorsCount() {
		return visitorsCount;
	}
	public void setVisitorsCount(Long visitorsCount) {
		this.visitorsCount = visitorsCount;
	}
	public Long getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	@OneToMany(mappedBy="assessment")
	public Set<AssessmentContent> getAssessmentContents() {
		return assessmentContents;
	}
	public void setAssessmentContents(Set<AssessmentContent> assessmentContents) {
		this.assessmentContents = assessmentContents;
	}
}
