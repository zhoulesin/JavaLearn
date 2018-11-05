package com.zhoulesin.biaoge.conf;

import com.zhoulesin.biaoge.tcpserver.mgmt.TcpFileProcess;
import com.zhoulesin.biaoge.thread.TcpCheckThread;

public class ReameProtocol {
	public static int PROCESS_SUCCESS = 0;
	public static int PROCESS_CLOSE = -1;
	public static int MAX_MSG_LEN = 8200;
	public static int UNF_BODY_LEN = 8192;
	public static int UPF_MSG_LEN = 2056;
	public static int UPF_BODY_LEN = 2048;
	public static final int ERR_CONN_SUCC = 0;
	public static final int START_SEND_FILE = 1;
	public static final int START_SENDINT =2;
	public static final int DOWN_SRC_PIC = 3;
	public static final int FILE_SEND_END = 15;
	public static final int ERR_INIT_FAILED = 16;
	public static final int ERR_CONN_FAILED = 17;
	public static final int ERR_CLOSE_FAILED = 18;
	public static final int ERR_SEND_TIME = 19;
	public static final int ERR_FILE_NOT = 20;
	public static final int ERR_FILE_OTH = 21;
	public static final int ERR_FILE_ZORE = 22;
	public static final int MAX_TIMEOUT = 45000;
	public static TcpCheckThread checkThread = null;
	public static final TcpFileProcess[] fileProcess=  new TcpFileProcess[3];
	
	static {
		fileProcess[0] = new TcpFileProcess();
	}
}
