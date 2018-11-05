package com.zhoulesin.biaoge.thread;

import com.zhoulesin.biaoge.conf.ReameProtocol;

public class TcpCheckThread extends Thread{
	private int WAIT_TIME = 10000;
	int result = 0;
	private int len = 0;
	
	public TcpCheckThread() {}
	
	@Override
	public void run() {
		this.len = ReameProtocol.fileProcess.length;
	}
	
}
