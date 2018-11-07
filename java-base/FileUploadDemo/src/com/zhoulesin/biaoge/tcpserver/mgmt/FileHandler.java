package com.zhoulesin.biaoge.tcpserver.mgmt;

public interface FileHandler {
	void initData();
	
	void dataStream(byte[] data);
}
