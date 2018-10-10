package com.zhoulesin.javabase.concurrentlinkeddequedemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
	public static void main(String[] args) {
		
		//创建非阻塞线程安全的列表ConcurrentLinkedDeque
		ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
//		List<String> list = new ArrayList<>();
		Thread[] threads = new Thread[100];
		
		//创建100个线程并运行
		for (int i = 0; i < threads.length; i++) {
			AddTask task = new AddTask(list);
			threads[i] = new Thread(task);
			threads[i].start();
		}
		
		System.out.printf("Main: %d AddTask threads have been launched\n",threads.length);
		
		//等待所有子线程运行完
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
				//调用线程会等待threads[i]走完之后再执行
				//见		Thread-join补充.md
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.printf("Main size of the list %d\n",list.size());
		
		//创建100个线程
		for (int i = 0; i < threads.length; i++) {
			PollTask task=  new PollTask(list);
			threads[i] = new Thread(task);
			threads[i].start();
		}
		System.out.printf("Main %d PollTask threads have been launched\n",threads.length);
		
		//等待线程执行完成
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.printf("Main : Size of the List : %d\n",list.size());
		
	}
}
