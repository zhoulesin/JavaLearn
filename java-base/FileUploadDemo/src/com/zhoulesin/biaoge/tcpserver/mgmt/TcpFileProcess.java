package com.zhoulesin.biaoge.tcpserver.mgmt;

import com.zhoulesin.biaoge.tcpserver.info.FileProcess;
import com.zhoulesin.biaoge.tcpserver.info.ProcessHandler;
import com.zhoulesin.biaoge.tcpserver.info.RealMeHandler;
import com.zhoulesin.biaoge.tcpserver.info.RealMeIoSession;
import com.zhoulesin.biaoge.thread.FilePool;

public class TcpFileProcess implements RealMeHandler,FileProcess{

	@Override
	public void fileProcess(ProcessHandler handler, FilePool pool) {
		
	}

	@Override
	public void monitor() {
		
	}

	@Override
	public void bussinessProcess(byte[] process) {
		
	}

	@Override
	public void connectSuccess(RealMeIoSession session) {
		
	}

	@Override
	public void connectClose(RealMeIoSession session) {
		
	}

	@Override
	public void connectFailed() {
		
	}

}
