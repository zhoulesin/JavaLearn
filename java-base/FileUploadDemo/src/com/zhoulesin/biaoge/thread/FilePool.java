package com.zhoulesin.biaoge.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.zhoulesin.biaoge.conf.ReameProtocol;
import com.zhoulesin.biaoge.tcpserver.info.ProcessHandler;

public class FilePool {
	private ProcessHandler[] queue;
	private final int MAX_QUEUE = 4;
	private int current = 0;
	private int savePosi = 0;
	private int getPosi = 0;
	private ReentrantLock lock = null;
	private Condition notEmpty = null;
	private Condition fullEmpty = null;
	
	private static FilePool filePool2 = null;
	private static FilePool filePool1 = null;
	
	private FilePool(int count) {
		this.queue = new ProcessHandler[ReameProtocol.MAX_MSG_LEN];
		this.lock = new ReentrantLock();
		this.notEmpty = this.lock.newCondition();
		this.fullEmpty = this.lock.newCondition();
		this.initThread(i);
	}
	
	public int getCount() {
		return this.current;
	}
	
	private void initThread(int i) {
		TaskProcessThread abThread1 = new TaskProcessThread(ReameProtocol.fileProcess[i],this);
		abThread1.start();
	}
}
