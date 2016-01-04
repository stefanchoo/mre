package com.mre.json.domin;

import java.io.Serializable;

public class AjaxRequireDataVO implements Serializable {
	
	private static final long serialVersionUID = -331162242016528094L;
	
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
	
	public AjaxRequireDataVO(float armSpeed, float armDegree,
			float shoulderSpeed, float shoulderDegree, float elbowSpeed,
			float elbowDegree, float positionX, float positionY,
			float positionZ, float samplingTime) {
		super();
		this.armSpeed = armSpeed;
		this.armDegree = armDegree;
		this.shoulderSpeed = shoulderSpeed;
		this.shoulderDegree = shoulderDegree;
		this.elbowSpeed = elbowSpeed;
		this.elbowDegree = elbowDegree;
		this.positionX = positionX;
		this.positionY = positionY;
		this.positionZ = positionZ;
		this.samplingTime = samplingTime;
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

	@Override
	public String toString() {
		return "AjaxRequireDataVO [armSpeed=" + armSpeed + ", armDegree="
				+ armDegree + ", shoulderSpeed=" + shoulderSpeed
				+ ", shoulderDegree=" + shoulderDegree + ", elbowSpeed="
				+ elbowSpeed + ", elbowDegree=" + elbowDegree + ", positionX="
				+ positionX + ", positionY=" + positionY + ", positionZ="
				+ positionZ + ", samplingTime=" + samplingTime + "]";
	}
}
