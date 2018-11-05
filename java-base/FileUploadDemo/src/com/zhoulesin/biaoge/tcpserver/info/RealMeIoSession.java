package com.zhoulesin.biaoge.tcpserver.info;

import java.nio.channels.SocketChannel;

public interface RealMeIoSession {
	void setSocketChannel(SocketChannel socketChannel);
	
	int getSessionId();
	
	SocketChannel getSocket();
	
	void writeData(byte[] data);
	
	void sendMessage(int mid, int cid, int seq, int status, int iid, byte[] body);
	
	void close();
}
