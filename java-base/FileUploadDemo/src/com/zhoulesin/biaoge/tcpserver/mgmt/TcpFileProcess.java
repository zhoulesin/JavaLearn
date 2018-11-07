package com.zhoulesin.biaoge.tcpserver.mgmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.zhoulesin.biaoge.tcpserver.info.FileProcess;
import com.zhoulesin.biaoge.tcpserver.info.ProcessHandler;
import com.zhoulesin.biaoge.tcpserver.info.RealMeHandler;
import com.zhoulesin.biaoge.tcpserver.info.RealMeIoSession;
import com.zhoulesin.biaoge.thread.FilePool;

public class TcpFileProcess implements RealMeHandler, FileProcess {

	TcpClient fileTcpClient = null;
	private final TcpFileProcess.FileInnerDownload fileDown = new TcpFileProcess.FileInnerDownload();
	private final TcpFileProcess.FileInnerUpload fileUp = new TcpFileProcess.FileInnerUpload();
	private int status = 0;
	FileHandler tmpHand = null;
	private int len = 0;
	private int bodyLen = 0;
	private byte[] tmp = null;
	private int ackSeq = 0;
	private int pkgStatus = 0;
	RealMeIoSession client = null;
	StringBuilder strBuilder = new StringBuilder(80);
	File fileload = null;
	FileOutputStream fos = null;
	FileInputStream fin = null;
	private int fileLen = 0;
	byte[] body = null;
	ProcessHandler _handler = null;
	private long timeInfo = 0L;
	private int faild = 0;
	private int seq = 0;
	private int tmpLen = 0;

	public TcpFileProcess(byte cmd) {
		this.fileTcpClient = TcpClient.getClient(cmd);
	}

	@Override
	public void businessProcess(byte[] msg) {
		this.pkgStatus = msg[3] & 255;
		if (this.pkgStatus == 15) {
			this.processResult(15);
		} else {
			this.timeInfo = System.currentTimeMillis();
			this.tmpHand.dataStream(msg);
		}
	}

	@Override
	public void connectSuccess(RealMeIoSession ioSession) {
		this.timeInfo = System.currentTimeMillis();
		this._handler.notify(0, 0, 0);
		this.client = ioSession;
		this.sendData();
	}

	private void sendData() {
		this.tmpHand.initData();
		this.client.writeData(this.body);
		this.status = 1;
	}

	@Override
	public void connectClose(RealMeIoSession session) {
		if (this.status != 0) {
			this.processResult(18);
		}
	}

	@Override
	public void connectFailed() {
		this.processResult(17);
	}

	@Override
	public void fileProcess(ProcessHandler handler, FilePool filePool) {
		try {
			if (handler.getNetwork() == 3) {
				this.processResult2(handler, 17);
				return;
			}

			synchronized (this) {
				this.status = 0;
				this.tmpLen = 0;
				this._handler = handler;
				this.fileload = null;
				this.faild = 0;
				if (this._handler.getUpLoadType() == 3) {
					this.tmpHand = this.fileDown;
				} else {
					this.tmpHand = this.fileUp;
				}

				this.timeInfo = System.currentTimeMillis();
				if (this.client != null && this.client.getSocket() != null
						&& !this.client.getSocket().socket().isClosed()) {
					this.sendData();
				} else {
					this.fileTcpClient.connectFileServer(handler.getRemoteIp(), handler.getPort(), this);
				}

				try {
					this.wait();
				} catch (Exception e) {
				}

				if (filePool.getCount() <= 0) {
					this.client.close();
				}

			}
		} catch (Exception e) {
		}
	}

	private void processResult2(ProcessHandler handler, int code) {
		this.timeInfo = 0L;
		this.status = 0;
		this.fos = null;
		this.fin = null;

		try {
			handler.notify(code, 0, 0);
		} catch (Exception e) {
		}
	}

	@Override
	public void monitor() {
		if (this.timeInfo != 0L) {
			if (System.currentTimeMillis() - this.timeInfo > 45000L) {
				this.timeInfo = 0L;

				try {
					this.client.close();
				} catch (Exception e) {
				}

				this.processResult(19);
			}
		}
	}

	private void processResult(int code) {
		try {
			this.timeInfo = 0L;
			this.status = 0;
			this.fos = null;
			this.fin = null;

			try {
				this._handler.notify(code, 0, 0);
			} catch (Exception e) {
			}

			if (code == 18 && this._handler.getNetwork() != 3) {
				Thread.sleep(3000L);
			}
		} catch (Exception e) {
		} finally {
			synchronized (this) {
				this.notify();
			}
		}
	}

	private void getFileId(byte[] msg) {
		int i = 0;
		boolean a = false;
		this.strBuilder.setLength(0);

		while (true) {
			char at = (char) msg[i];
			if (at == 107) { // k --107
				i += 2;

				while (true) {
					at = (char) msg[i];
					if (at == 60) {
						this._handler.setFileId(this.strBuilder.toString());
						return;
					}

					this.strBuilder.append(at);
					++i;
				}
			}

			++i;
		}
	}

	private void getFileLen(byte[] msg) {
		int i = 0;
		boolean a = false;
		this.strBuilder.setLength(0);

		while (true) {
			char at = (char) msg[i];
			if (at == 116) { // t---116
				i += 2;
				while (true) {
					at = (char) msg[i];
					if (at == 60) {
						this.tmpLen = Integer.parseInt(this.strBuilder.toString());
						return;
					}

					this.strBuilder.append(at);
					++i;
				}
			}
			++i;
		}
	}
	
	public class FileInnerDownload implements FileHandler {
		private boolean append = false;
		private byte[] abcd = new byte[8];
		private int sendLen = 0;
		private char a = 0;
		private char tmpChar = 50;
		private int bgMsgLen = 0;
		
		FileInnerDownload() {
		}
		
		@Override
		public void initData() {
			TcpFileProcess.this.fin = null;
			this.sendLen = 0;
			TcpFileProcess.this.tmpLen = 0;
			TcpFileProcess.this.status = 0;
			
		}

		@Override
		public void dataStream(byte[] data) {

		}

	}

	public class FileInnerUpload implements FileHandler {
		@Override
		public void initData() {

		}

		@Override
		public void dataStream(byte[] data) {

		}
	}

	
}
