package com.mre.util;


public class MathFormula {
	public static float L1 = 233f; // 单位毫米
	public static float L2 = 375f;
	public static float L3 = 452f;
	
	
	/**
	 * 根据传递过来的电机角度得到关节角度（单位是 /度 °）
	 * @param d1  ： 大关节电机角度
	 * @param d2  ： 肩关节电机角度 
	 * @param d3 ： 肘关节电机角度
	 * @return degree[] ： 转换之后的关节角度
	 */
	public static float[] nativeData2TrueDeg(float d1, float d2, float d3){
		float degree[] = {0f, 0f, 0f};
		
		// 电机角度转换成关节角度
		degree[0] = d1;
		degree[1] = (float) (0.4368*(d2 - d1));    
		degree[2] = (float)(-2*d1 + d2 + d3);
		
		return degree;
	}
	
	/**
	 * 根据原始数据得到位置坐标
	 * @param d1
	 * @param d2
	 * @param d3
	 * @return
	 */
	public static float[] nativeData2Position(float d1, float d2, float d3) {
		float position[] = {0f, 0f, 0f};
		
		float degree[] = nativeData2TrueDeg(d1, d2, d3);
		
		// 将角度转换成弧度 (very  important!)
		float theta1 = (float) Math.toRadians(degree[0]);
		float theta2 = (float) Math.toRadians(degree[1]);
		float theta3 = (float) Math.toRadians(degree[2]);
		
		// 得到坐标值
		position[0] = (float) (L3 * Math.cos(theta1) * Math.cos(theta2 + theta3) + L2
				* Math.cos(theta1) * Math.cos(theta2));
		position[1] = (float) (L3 * Math.sin(theta1) * Math.cos(theta2 + theta3) + L2
				* Math.sin(theta1) * Math.cos(theta2));
		position[2] = (float) (-L3 * Math.sin(theta2 + theta3) - L2 * Math.sin(theta2));
		
		return position;
	}
	
	/**
	 * 根据处理后的角度得到位置坐标
	 * @param degree
	 * @return
	 */
	public static float[] trueDeg2Position(float[] degree) {
		float position[] = {0f, 0f, 0f};
		
		// 将角度转换成弧度 (very  important!)
		float theta1 = (float) Math.toRadians(degree[0]);
		float theta2 = (float) Math.toRadians(degree[1]);
		float theta3 = (float) Math.toRadians(degree[2]);
		
		// 得到坐标值
		position[0] = (float) (L3 * Math.cos(theta1) * Math.cos(theta2 + theta3) + L2
				* Math.cos(theta1) * Math.cos(theta2));
		position[1] = (float) (L3 * Math.sin(theta1) * Math.cos(theta2 + theta3) + L2
				* Math.sin(theta1) * Math.cos(theta2));
		position[2] = (float) (-L3 * Math.sin(theta2 + theta3) - L2 * Math.sin(theta2));
		
		return position;
	}
}
