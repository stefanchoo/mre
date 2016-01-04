package com.mre.json.domin;

import java.io.Serializable;

/**
 * 接收数据的值对象
 * @author Africa
 */
public class CentrobotDataVO implements Serializable{
	private static final long serialVersionUID = 963216972258239133L;
	
	private float armVelocity;
	private float shoulderVelocity;
	private float elbowVelocity;
	private float armAngle;
	private float shoulderAngle;
	private float elbowAngle;
	private float samplingTime;
	
	public CentrobotDataVO(float armVelocity, float shoulderVelocity,
			float eldowVelocity, float armAngle, float shoulderAngle,
			float eldowAngle, float samplingTime) {
		super();
		this.armVelocity = armVelocity;
		this.shoulderVelocity = shoulderVelocity;
		this.elbowVelocity = eldowVelocity;
		this.armAngle = armAngle;
		this.shoulderAngle = shoulderAngle;
		this.elbowAngle = eldowAngle;
		this.samplingTime = samplingTime;
	}
	
	public float getArmVelocity() {
		return armVelocity;
	}
	public void setArmVelocity(float armVelocity) {
		this.armVelocity = armVelocity;
	}
	public float getShoulderVelocity() {
		return shoulderVelocity;
	}
	public void setShoulderVelocity(float shoulderVelocity) {
		this.shoulderVelocity = shoulderVelocity;
	}
	public float getElbowVelocity() {
		return elbowVelocity;
	}
	public void setElbowVelocity(float eldowVelocity) {
		this.elbowVelocity = eldowVelocity;
	}
	public float getArmAngle() {
		return armAngle;
	}
	public void setArmAngle(float armAngle) {
		this.armAngle = armAngle;
	}
	public float getShoulderAngle() {
		return shoulderAngle;
	}
	public void setShoulderAngle(float shoulderAngle) {
		this.shoulderAngle = shoulderAngle;
	}
	public float getElbowAngle() {
		return elbowAngle;
	}
	public void setElbowAngle(float elbowAngle) {
		this.elbowAngle = elbowAngle;
	}
	public float getSamplingTime() {
		return samplingTime;
	}
	public void setSamplingTime(float samplingTime) {
		this.samplingTime = samplingTime;
	}

	@Override
	public String toString() {
		return "CentrobotDataVO [armVelocity=" + armVelocity
				+ ", shoulderVelocity=" + shoulderVelocity + ", eldowVelocity="
				+ elbowVelocity + ", armAngle=" + armAngle + ", shoulderAngle="
				+ shoulderAngle + ", eldowAngle=" + elbowAngle
				+ ", samplingTime=" + samplingTime + "]";
	}
}
