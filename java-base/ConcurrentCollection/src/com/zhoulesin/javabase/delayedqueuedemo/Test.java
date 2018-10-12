package com.zhoulesin.javabase.delayedqueuedemo;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<Event> queue = new DelayQueue<>();
		Thread[] threads = new Thread[5];
		for (int i = 0; i < threads.length; i++) {
			Task task = new Task(i, queue);
			threads[i] = new Thread(task);
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		do {
			int counter = 0;
			Event event;
			do {
				event = queue.poll();
				if (event != null) {
					counter ++;
				}
			} while (event != null);
			System.out.printf("at %s you have read %d events\n",new Date(),counter);
			TimeUnit.MILLISECONDS.sleep(500);
		} while (queue.size()>0);
	}
}
