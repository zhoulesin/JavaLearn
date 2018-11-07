package com.zhoulesin.biaoge.tcpserver.info;

import java.nio.channels.SocketChannel;

import com.zhoulesin.biaoge.conf.ReameProtocol;
import com.zhoulesin.biaoge.tcpserver.ts.RealmeTcpServer;

public class RealMeTcpSession implements RealMeIoSession{
	private byte sessionId;
	private SocketChannel socket = null;
	RealmeTcpServer tsClient = null;
	long ti = 0L;
	
	public RealMeTcpSession(byte bt) {
		this.sessionId = bt;
	}

	@Override
	public void setSocketChannel(SocketChannel socketChannel) {
		this.socket = socketChannel;
	}

	@Override
	public int getSessionId() {
		return this.sessionId;
	}

	@Override
	public SocketChannel getSocket() {
		return this.socket;
	}

	@Override
	public void writeData(byte[] bt) {
		this.tsClient.sendToServer(bt);
	}

	@Override
	public void sendMessage(int mid, int cid, int seq, int status, int iid, byte[] body) {
		this.tsClient.sendToServer(mid,cid,seq,status,iid,body);
	}

	@Override
	public void close() {
		this.tsClient.stopClient();
	}

	public void setTsClient(RealmeTcpServer tsClient) {
		this.tsClient = tsClient;
	}

}
