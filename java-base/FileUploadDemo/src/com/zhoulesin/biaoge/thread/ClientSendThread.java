package com.zhoulesin.biaoge.thread;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.zhoulesin.biaoge.conf.ReameProtocol;
import com.zhoulesin.biaoge.tcpserver.info.RealMeIoSession;
import com.zhoulesin.biaoge.tcpserver.info.RealMeTcpSession;
import com.zhoulesin.biaoge.tcpserver.tool.ByteTools;

public class ClientSendThread extends Thread {

	private byte[][] queue;
	private final int MAX_QUEUE = 5;
	private int current = 0;
	private int savePosi = 0;
	private int getPosi = 0;
	private ReentrantLock lock = null;
	private Condition notEmpty = null;
	private Condition fullEmpty = null;
	private RealMeIoSession ioSession = null;
	private ByteBuffer bedd;
	private int len;
	private int sendLen;
	private int ffEnd;
	byte[] msg;

	public ClientSendThread() {
		this.bedd = ByteBuffer.allocate(ReameProtocol.MAX_MSG_LEN);
		this.len = 0;
		this.sendLen = 0;
		this.ffEnd = 0;
		this.msg = null;
		this.queue = new byte[5][ReameProtocol.MAX_MSG_LEN];
		this.lock = new ReentrantLock();
		this.notEmpty = this.lock.newCondition();
		this.fullEmpty = this.lock.newCondition();
	}

	public void addData(byte[] body) {
		ReentrantLock lock = this.lock;

		try {
			lock.lock();
			if (this.current >= 5) {
				this.fullEmpty.await();
			}
			if (this.savePosi >= 5) {
				this.savePosi = 0;
			}

			System.arraycopy(body, 0, this.queue[this.savePosi], 0, body.length);
			++this.savePosi;
			--this.current;
			this.notEmpty.signal();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		while (true) {
			while (true) {
				try {
					this.getMsgByte();
					if (!this.ioSession.getSocket().socket().isClosed()) {
						this.len = ByteTools.byte2ToShort(this.msg, 1) + 3;
						this.bedd.position(0);
						this.bedd.limit(ReameProtocol.MAX_MSG_LEN);
						this.bedd.put(this.msg, 0, this.len);
						this.bedd.flip();
						this.sendLen = this.ioSession.getSocket().write(this.bedd);

						for (this.ffEnd = 0; this.len != this.sendLen; this.sendLen = this.ioSession.getSocket()
								.write(this.bedd)) {
							sleep(10);
							this.len -= this.sendLen;
							this.ffEnd += this.sendLen;
							this.bedd.position(0);
							this.bedd.limit(ReameProtocol.MAX_MSG_LEN);
							this.bedd.put(this.msg, this.ffEnd, this.len);
							this.bedd.flip();
						}

						this.msg = null;

					}
				} catch (Exception e) {
				}
			}
		}
	}

	private void getMsgByte() {
		ReentrantLock lock = this.lock;
		try {
			lock.lock();
			if (this.current <= 0) {
				this.current = 0;

				try {
					this.notEmpty.await();
				} catch (Exception e) {
				}
			}

			if (this.getPosi >= 5) {
				this.getPosi = 0;
			}

			this.msg = this.queue[this.getPosi];
			++this.getPosi;
			--this.current;
			this.fullEmpty.signalAll();
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	public void setIosession(RealMeTcpSession ioSession) {
		this.ioSession = ioSession;
	}

}
