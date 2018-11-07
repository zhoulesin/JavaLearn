package com.zhoulesin.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo2 {
	static List<Integer> array = new ArrayList<>();
	static List<Integer> linked = new LinkedList<>();

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			array.add(i);
			linked.add(i);
		}
		System.out.println("ArrayList插入消耗的时间:" + insertTime(array));
		System.out.println("LinkedList插入消耗的时间:" + insertTime(linked));
		
		//结果
		//-array 27
		//-linked 2
	}

	public static long insertTime(List list) {
		long time = System.currentTimeMillis();
		for (int i = 100; i < 10000; i++) {
			list.add(10, i);
		}
		return System.currentTimeMillis() - time;
	}
}
