package com.lmax.disruptor.study.singleproducer;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityHashMapTest {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("a", "1");
		map.put("a", "2");
		map.put("a", "3");
		System.out.println(map.size()); // 1

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put(new String("a"), "1");
		hashMap.put(new String("a"), "2");
		hashMap.put(new String("a"), "3");
		System.out.println(hashMap.size()); // 1
		
		// 这个是通过hashcode和equals进行比较的
		Map<Integer, String> hashMap2 = new HashMap<>();
		hashMap2.put(new Integer(200), "1");
		hashMap2.put(new Integer(200), "2");
		hashMap2.put(new Integer(200), "3");
		System.out.println(hashMap2.size()); // 1
		
		// 这个是通过比较地址的 永远比较的都是地址 不管class有没有重写hashcode方法
		IdentityHashMap<String,String> idMap = new IdentityHashMap<String,String>();
		String str = new String("a");
		String str1 = new String("a");
		String str2 = new String("a");
		idMap.put(str, "1");
		idMap.put(str1, "2");
		idMap.put(str2, "3");
		System.out.println(idMap.size() + " ," + idMap.get(str) + "," + idMap.get(str1) + "," + idMap.get(str2)); // 1
		
		String a = "a";
		// System.identityHashCode()方法的作用是返回hashcode值不管是不是重写都是返回对象的hashcode
		System.out.println(System.identityHashCode(null) + " " + a.hashCode());
		
		// 转化为二进制
//		System.out.println(toBinary(11));// 1000 1011
		// 转为十进制
//		System.out.println(Integer.parseInt("1000",2));
		// 抑或 相同为0 不相同为1
//		System.out.println(8 ^ 11);
	}

	// 转为为二进制的字符串的表示
	static String toBinary(int num) {
		String str = "";
		while (num != 0) {
			str = num % 2 + str;
			num = num / 2;
		}
		return str;
	}
	
	
	
	
}
