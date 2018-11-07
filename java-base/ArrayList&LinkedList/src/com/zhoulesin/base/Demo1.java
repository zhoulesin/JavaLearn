package com.zhoulesin.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Demo1 {
	static List<Integer> array = new ArrayList<>();
	static List<Integer> linked = new LinkedList<>();
	
	public static void main(String[] args) {
		for(int i = 0;i < 10000;i++) {
			array.add(i);
			linked.add(i);
		}
		System.out.println("ArrayList访问消耗时间 : " + getTime(array));
		System.out.println("LinkedList访问消耗时间 : " + getTime(linked));
		
		//打印
		//2
		//220
		//-- 对于随机访问,ArrayList的访问速度更快
	}
	
	public static long getTime(List list) {
		long time = System.currentTimeMillis();
		for(int i = 0;i < 10000;i++) {
			int index = Collections.binarySearch(list, list.get(i));
			if(index != i) {
				System.out.println("ERROR!");
			}
		}
		return System.currentTimeMillis() - time;
	}
}
