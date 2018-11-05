package com.zhoulesin.biaoge.tcpserver.info;

import com.zhoulesin.biaoge.thread.FilePool;

public interface FileProcess {
	void fileProcess(ProcessHandler handler,FilePool pool);
	
	void monitor();
}
