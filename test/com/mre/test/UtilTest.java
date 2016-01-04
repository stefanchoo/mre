package com.mre.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class UtilTest {
	@Test
	public void testString() {
		String aa = "上肢康复助力训练";
		String bb = aa.split("复")[1];
		System.out.println(bb);
	}

	@Test
	public void testSortList() {
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < 9; i++) {
			list.add("a" + i);
		}
		Collections.sort(list);// 顺序排列
		System.out.println(list);
		
		Collections.reverse(list);// 倒序排列
		System.out.println(list);
		
		Collections.shuffle(list);// 混乱的意思
		System.out.println(list);

		System.out.println(Collections.binarySearch(list, "a5"));// 折半查找
	}
	
	@Test
	public void testMD5() {
		String devUID = "sz-centrobot-0001";
		String md5Str = DigestUtils.md5Hex(devUID);
		System.out.println("md5Str="+md5Str);
	}
	
	@Test
	public void testMap() {
		Map<String, String> strMap = new HashMap<String, String>();
		strMap.put("sss", "aaa");
		// 实验1： 证明map中key是唯一的
//		strMap.put("sss", "aaa");  
		// 实验2： 证明同一个key值，后添加的value会替换之前的
		strMap.put("sss", "bbb"); 
		for(String key : strMap.keySet()){
			System.out.println(strMap.get(key));
		}
	}
	
	/**
	 * 测试 list 
	 */
	@Test
	public void testList(){
		List<String> strList = new ArrayList<String>();
		strList.add("aaa");
		strList.add("bbb");
		strList.remove(0);
		System.out.println(strList.get(0));
	}
}
