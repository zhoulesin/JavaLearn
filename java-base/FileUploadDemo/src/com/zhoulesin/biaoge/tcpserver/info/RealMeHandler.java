package com.zhoulesin.biaoge.tcpserver.info;

public interface RealMeHandler {
	void businessProcess(byte[] data);
	
	void connectSuccess(RealMeIoSession session);
	
	void connectClose(RealMeIoSession session);
	
	void connectFailed();
}
