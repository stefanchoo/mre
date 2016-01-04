package com.mre.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.mre.util.Formatter;

@Entity
@Table(name="_centrobotrehabplan")
public class CentrobotRehabPlan implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8883434905042307323L;
	private Long id;
	private String trainingType;             // 训练类型
	private String subTrainingType;          // 次要训练类型
	private String trainingMotion;           // 训练动作
	private int trainingCount;               // 训练次数
	private int timePerTraining;             // 每次时长
	private int forceValue;                   // 施力大小
	private String forceRange;                // 施力强度
	private Date startTime;                   // 开始时间
	private Date endTime;                     // 结束时间
	private String graph;                     // 训练完成后的图表url
	// one to one 的关系
	private RehabPlan rehabPlan;              // 与之对应的康复计划
	
	// one to many 的双向关系
	private Set<CentrobotData> centrobotDatas = new HashSet<CentrobotData>() ;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=20)
	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	@Column(length=20)
	public String getSubTrainingType() {
		return subTrainingType;
	}

	public void setSubTrainingType(String subTrainingType) {
		this.subTrainingType = subTrainingType;
	}

	@Column(length=40)
	public String getTrainingMotion() {
		return trainingMotion;
	}

	public void setTrainingMotion(String trainingMotion) {
		this.trainingMotion = trainingMotion;
	}
	public int getTrainingCount() {
		return trainingCount;
	}

	public void setTrainingCount(int trainingCount) {
		this.trainingCount = trainingCount;
	}

	public int getTimePerTraining() {
		return timePerTraining;
	}

	public void setTimePerTraining(int timePerTraining) {
		this.timePerTraining = timePerTraining;
	}

	public int getForceValue() {
		return forceValue;
	}

	public void setForceValue(int forceValue) {
		this.forceValue = forceValue;
	}
	
	@Column(length=5)
	public String getForceRange() {
		return forceRange;
	}

	public void setForceRange(String forceRange) {
		this.forceRange = forceRange;
	}
	
	@Type(type="timestamp")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Type(type="timestamp")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(length=30)
	public String getGraph() {
		return graph;
	}
	public void setGraph(String graph) {
		this.graph = graph;
	}
	// 一对一的关系
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="rehabPlanId")
	public RehabPlan getRehabPlan() {
		return rehabPlan;
	}

	public void setRehabPlan(RehabPlan rehabPlan) {
		this.rehabPlan = rehabPlan;
	}
	
	@OneToMany(mappedBy="centrobotRehabPlan")
	public Set<CentrobotData> getCentrobotDatas() {
		return centrobotDatas;
	}

	public void setCentrobotDatas(Set<CentrobotData> centrobotDatas) {
		this.centrobotDatas = centrobotDatas;
	}

	/**
	 * 显示 2015-05-01 被动训练 这种样式
	 * @return
	 */
	@Transient
	public String basicShow() {
		return Formatter.formatm(this.getRehabPlan().getTime())+" "+this.getTrainingType();
	}
}
