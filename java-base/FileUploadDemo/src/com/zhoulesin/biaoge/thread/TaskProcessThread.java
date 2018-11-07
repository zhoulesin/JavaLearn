package com.zhoulesin.biaoge.thread;

import com.zhoulesin.biaoge.tcpserver.info.ProcessHandler;
import com.zhoulesin.biaoge.tcpserver.mgmt.TcpFileProcess;

public class TaskProcessThread extends Thread {

	private TcpFileProcess _fileProcess = null;
	private FilePool filePool = null;
	ProcessHandler msg = null;

	public TaskProcessThread(TcpFileProcess fileProcess, FilePool _filePool) {
		this._fileProcess = fileProcess;
		this.filePool = _filePool;
	}

	public void run() {
		while (true) {
			try {
				this.msg = this.filePool.getMsgByte();
				long ee = System.currentTimeMillis();
				this._fileProcess.fileProcess(this.msg, this.filePool);
			} catch (Exception e) {
			}
		}
	}
}
