package com.zhoulesin.biaoge.tcpserver.mgmt;

import com.zhoulesin.biaoge.tcpserver.info.RealMeHandler;
import com.zhoulesin.biaoge.tcpserver.ts.RealmeTcpServer;

final class TcpClient {
	private RealmeTcpServer realMeTcp = null;
	
	public static TcpClient getClient(byte cmd) {
		TcpClient client = new TcpClient();
		client.initTcpClient(cmd);
		return client;
	}
	private TcpClient() {}
	
	private void initTcpClient(byte cmd) {
		this.realMeTcp = new RealmeTcpServer(cmd);
	}
	
	public void connectFileServer(String serverIp,int serverPort,RealMeHandler bussiness) {
		this.realMeTcp.connect(serverIp,serverPort,business);
	}
}
