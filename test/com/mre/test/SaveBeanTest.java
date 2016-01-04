package com.mre.test;

import org.junit.Test;

public class SaveBeanTest {
	@Test
	public void testTime(){
		// 以毫秒为单位的
	    Long currentTime = System.currentTimeMillis();
		long time = 1000000000L;
		while(time-- != 0);
		System.out.println(System.currentTimeMillis()-currentTime);
		}
}
