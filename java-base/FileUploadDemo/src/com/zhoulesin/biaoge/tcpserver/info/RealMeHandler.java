package com.zhoulesin.biaoge.tcpserver.info;

public interface RealMeHandler {
	void bussinessProcess(byte[] process);
	
	void connectSuccess(RealMeIoSession session);
	
	void connectClose(RealMeIoSession session);
	
	void connectFailed();
}
