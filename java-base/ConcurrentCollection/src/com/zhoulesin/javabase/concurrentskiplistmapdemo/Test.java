package com.zhoulesin.javabase.concurrentskiplistmapdemo;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class Test {
	public static void main(String[] args) {
		ConcurrentSkipListMap<String, Contact> map = new ConcurrentSkipListMap<>();
		Thread[] threads = new Thread[25];
		int counter = 0;
		
		for (char i = 'A'; i < 'Z'; i++) {
			Task task = new Task(map, String.valueOf(i));
			threads[counter] = new Thread(task);
			threads[counter].start();
			counter++;
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.printf("Main:Size of the map %d\n",map.size());
		Map.Entry<String, Contact> element;
		Contact contact;
		element = map.firstEntry();
		
	}
}
