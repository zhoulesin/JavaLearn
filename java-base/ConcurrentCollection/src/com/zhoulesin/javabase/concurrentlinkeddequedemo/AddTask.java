package com.zhoulesin.javabase.concurrentlinkeddequedemo;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AddTask implements Runnable{
	
	private ConcurrentLinkedDeque<String> list;
	
	public AddTask(ConcurrentLinkedDeque<String> list) {
		this.list = list;
	}
	
//	private List<String> list;
//	public AddTask(List<String> list) {
//		this.list = list;
//	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		for (int i = 0; i < 10000; i++) {
			list.add(name +":Element " + i);
		}
	}

}
