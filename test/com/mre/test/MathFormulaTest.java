package com.mre.test;

import org.junit.Test;

import com.mre.util.MathFormula;

public class MathFormulaTest {

	@Test
	public void testGetTrueDeg() {
		float d1 = -0.9f;
		float d2 = -1.98f;
		float d3 = -4.86f;
		long t1 = System.currentTimeMillis();
		float degree[] = MathFormula.nativeData2TrueDeg(d1, d2, d3);
		long t2 = System.currentTimeMillis();
		System.out.println("角度值：(" + degree[0] + ", " + degree[1] + ", "
				+ degree[2] + ")");
		System.out.println("耗时：" + (t2 - t1));
	}

	@Test
	public void testGetPosition() {
		float d1 = -0.9f;
		float d2 = -1.98f;
		float d3 = -4.86f;
		long t1 = System.currentTimeMillis();
//		System.out.println("开始时间：" + t1);
//		for (int i = 0; i < 100; i++) {                 // 测试处理数据还是很快的
			float position[] = MathFormula.nativeData2Position(d1, d2, d3);
//		}
		long t2 = System.currentTimeMillis();
//		System.out.println("开始时间：" + t2);
		 System.out.println("坐标值：(" + position[0] + ", " + position[1] + ", "
		 + position[2] + ")");
		System.out.println("耗时：" + (t2 - t1));
	}
	
	@Test
	public void testFloat() {
		float a = 123.2334f;  
		float b = (float)(Math.round(a*10))/10;
		System.out.println(b);
	}
}
