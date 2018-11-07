package com.zhoulesin.javabase.concurrentnavigablemapdemo;

import java.util.concurrent.ConcurrentSkipListMap;

public class Test {
	//ConcurrentNavigableMap 接口
	//实现该接口的类存储两部分元素
	//-唯一标识元素的key
	//-定义元素的剩余数据
	
	
	public static void main(String[] args) {
		ConcurrentSkipListMap<String,Contact> map = new ConcurrentSkipListMap<>();
		Thread[] threads = new Thread[25];
		int counter = 0;
		for(char i='A';i<'Z';i++) {
			Task task = new Task(map, String.valueOf(i));
			threads[counter] = new Thread(task);
			threads[counter].start();
			counter++;
		}
		
		for(int i=0;i<25;i++) {
			try {
				threads[i].join();
			} catch (Exception e) {
			}
		}
		
	}
}
