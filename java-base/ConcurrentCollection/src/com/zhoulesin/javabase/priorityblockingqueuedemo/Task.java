package com.zhoulesin.javabase.priorityblockingqueuedemo;

import java.util.concurrent.PriorityBlockingQueue;

public class Task implements Runnable{
	
	private int id;
	private PriorityBlockingQueue<Event> queue;

	public Task(int id, PriorityBlockingQueue<Event> queue) {
		super();
		this.id = id;
		this.queue = queue;
	}

	@Override
	public void run() {
		for (int i =0 ;i<100;i++) {
			Event event = new Event(id, i);
			queue.add(event);
		}
	}

}
