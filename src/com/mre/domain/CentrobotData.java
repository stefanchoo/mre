package com.mre.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mre.util.MathFormula;

@Entity
@Table(name = "_centrobotdata")
/**
 * 用来存储上肢康复机器人上传上来的六条数据
 * @author Administrator
 *
 */
public class CentrobotData implements Serializable {

	private static final long serialVersionUID = -5112856686343285914L;

	private Long id;
	private String devUID; // 设备编号，由equipment中的number来标识

	private float armSpeed; // 大关节的角速度
	private float armDegree; // 大关节的角度
	private float shoulderSpeed; // 肩关节的角速度
	private float shoulderDegree; // 肩关节的角度
	private float elbowSpeed; // 肘关节的角速度
	private float elbowDegree; // 肘关节的角度
	private float positionX; // 坐标点x
	private float positionY; // 坐标点y
	private float positionZ; // 坐标点z

	private float samplingTime; // 采样时间 （100ms一个）

	// 多对一的双向关系 与康复计划
	private CentrobotRehabPlan centrobotRehabPlan;

	// 多对一的单向关系（其实从CentrobotRehabPlan是可以得到equipment的，但是为了省去访问的时间，这里做一个单向链接）
	private EquipmentUsed equipmentUsed;

	// 1. 构造方法1
	public CentrobotData() {

	}

	// 2. 构造方法2
	/**
	 * 
	 * @param armSpeed
	 * @param nArmDegree
	 *            : native data
	 * @param shoulderSpeed
	 * @param nShoulderDegree
	 *            : native data
	 * @param eldowSpeed
	 * @param nEldowDegree
	 *            : native data
	 * @param samplingTime
	 */
	public CentrobotData(float armSpeed, float nArmDegree, float shoulderSpeed,
			float nShoulderDegree, float elbowSpeed, float nElbowDegree,
			float samplingTime, CentrobotRehabPlan centrobotRehabPlan) {

		this.armSpeed = armSpeed;
		this.shoulderSpeed = shoulderSpeed;
		this.elbowSpeed = elbowSpeed;

		// native data --> true degree
		float degree[] = MathFormula.nativeData2TrueDeg(nArmDegree,
				nShoulderDegree, nElbowDegree);

		this.armDegree = degree[0];
		this.shoulderDegree = degree[1];
		this.elbowDegree = degree[2];

		this.samplingTime = samplingTime;

		// true degree --> position
		float position[] = MathFormula.trueDeg2Position(degree);

		this.positionX = position[0];
		this.positionY = position[1];
		this.positionZ = position[2];

		this.centrobotRehabPlan = centrobotRehabPlan;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 30)
	public String getDevUID() {
		return devUID;
	}

	public void setDevUID(String devUID) {
		this.devUID = devUID;
	}

	public float getArmSpeed() {
		return armSpeed;
	}

	public void setArmSpeed(float armSpeed) {
		this.armSpeed = armSpeed;
	}

	public float getArmDegree() {
		return armDegree;
	}

	public void setArmDegree(float armDegree) {
		this.armDegree = armDegree;
	}

	public float getShoulderSpeed() {
		return shoulderSpeed;
	}

	public void setShoulderSpeed(float shoulderSpeed) {
		this.shoulderSpeed = shoulderSpeed;
	}

	public float getShoulderDegree() {
		return shoulderDegree;
	}

	public void setShoulderDegree(float shoulderDegree) {
		this.shoulderDegree = shoulderDegree;
	}

	public float getElbowSpeed() {
		return elbowSpeed;
	}

	public void setElbowSpeed(float elbowSpeed) {
		this.elbowSpeed = elbowSpeed;
	}

	public float getElbowDegree() {
		return elbowDegree;
	}

	public void setElbowDegree(float elbowDegree) {
		this.elbowDegree = elbowDegree;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public float getPositionZ() {
		return positionZ;
	}

	public void setPositionZ(float positionZ) {
		this.positionZ = positionZ;
	}

	public float getSamplingTime() {
		return samplingTime;
	}

	public void setSamplingTime(float samplingTime) {
		this.samplingTime = samplingTime;
	}

	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name = "centrobotRehabPlanId")
	public CentrobotRehabPlan getCentrobotRehabPlan() {
		return centrobotRehabPlan;
	}

	public void setCentrobotRehabPlan(CentrobotRehabPlan centrobotRehabPlan) {
		this.centrobotRehabPlan = centrobotRehabPlan;
	}

	@ManyToOne
	@JoinColumn(name = "equipmentUsedId")
	public EquipmentUsed getEquipmentUsed() {
		return equipmentUsed;
	}

	public void setEquipmentUsed(EquipmentUsed equipmentUsed) {
		this.equipmentUsed = equipmentUsed;
	}

	@Override
	public String toString() {
		return "CentrobotData [id=" + id + ", devUID=" + devUID + ", armSpeed="
				+ armSpeed + ", armDegree=" + armDegree + ", shoulderSpeed="
				+ shoulderSpeed + ", shoulderDegree=" + shoulderDegree
				+ ", elbowSpeed=" + elbowSpeed + ", elbowDegree=" + elbowDegree
				+ ", positionX=" + positionX + ", positionY=" + positionY
				+ ", positionZ=" + positionZ + ", samplingTime=" + samplingTime
				+ ", centrobotRehabPlan=" + centrobotRehabPlan
				+ ", equipmentUsed=" + equipmentUsed + "]";
	}	
}
