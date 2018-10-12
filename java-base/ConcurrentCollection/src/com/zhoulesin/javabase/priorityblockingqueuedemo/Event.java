package com.zhoulesin.javabase.priorityblockingqueuedemo;

public class Event implements Comparable<Event>{
	//已创建事件的线程数
	private int thread;
	//事件的优先级
	private int priority;

	public Event(int thread, int priority) {
		super();
		this.thread = thread;
		this.priority = priority;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Event e) {
		if(this.priority>e.priority) {
			return -1;
		}else if(this.priority < e.priority) {
			return 1;
		}else {
			return 0;
		}
	}

}
