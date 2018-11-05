package com.zhoulesin.biaoge.tcpserver.ts;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.zhoulesin.biaoge.conf.ReameProtocol;
import com.zhoulesin.biaoge.tcpserver.info.RealMeHandler;
import com.zhoulesin.biaoge.tcpserver.info.RealMeIoSession;
import com.zhoulesin.biaoge.tcpserver.info.RealMeTcpSession;
import com.zhoulesin.biaoge.tcpserver.tool.ByteTools;
import com.zhoulesin.biaoge.thread.ClientSendThread;

public class RealmeTcpServer {

	public class TcpReadThread extends Thread {
		private final int MAX_QUENUE = 9;
		private int current = 0;
		private int savePosi = 0;
		private int getPosi = 0;
		private ReentrantLock lock = null;
		private Condition notEmpty = null;
		private Condition fullEmpty = null;
		private final int MAX_MSG;
		private byte[][] keys;
		byte ab;
		RealMeHandler tmpHandler;

		public TcpReadThread() {
			this.MAX_MSG = ReameProtocol.MAX_MSG_LEN;
			this.keys = new byte[9][this.MAX_MSG];
			this.ab = 0;
			this.tmpHandler = null;
			this.lock = new ReentrantLock();
			this.notEmpty = this.lock.newCondition();
			this.fullEmpty = this.lock.newCondition();
		}

		public void run() {
			while (!interrupted()) {
				this.getTask();
			}
		}

		public void addTask(byte[] body, int len) {
			ReentrantLock _lock = this.lock;
			try {
				_lock.lock();
				if (this.current >= 9) {
					this.fullEmpty.await();
				}

				if (this.savePosi >= 9) {
					this.savePosi = 0;
				}

				System.arraycopy(body, 0, this.keys[this.savePosi], 0, len);
				++this.savePosi;
				--this.current;
				this.notEmpty.signal();
			} catch (Exception e) {
			} finally {
				_lock.unlock();
			}
		}

		public void getTask() {
			ReentrantLock _lock = this.lock;
			try {
				_lock.lock();

				while (this.current <= 0) {
					try {
						this.notEmpty.await();
					} catch (Exception e) {
					}
				}

				if (this.getPosi >= 9) {
					this.getPosi = 0;
				}

				this.ab = this.keys[this.getPosi][4];
				this.tmpHandler = ReameProtocol.fileProcess[this.ab];
				this.keys[this.getPosi][4] = 0;
				this.tmpHandler.bussinessProcess(this.keys[this.getPosi]);
				++this.getPosi;
				--this.current;
				this.fullEmpty.signal();
			} catch (Exception e) {
			} finally {
				_lock.unlock();
			}

		}
	}

	public class ClientProcess extends Thread {
		RealmeTcpServer.TcpReadThread read = null;
		private Selector selector = null;
		private InetSocketAddress remoteSocket = null;
		private SocketChannel channel = null;
		int ready = 0;
		private final int MAX_MSG;
		private byte[] ab;
		private int readLen;
		private ByteBuffer buffer;
		private int msgLen;
		private int totalLen;
		private int byteLen;
		
		public RealMeTcpSession getClient() {
			return RealmeTcpServer.this.ioSession;
		}
		
		ClientProcess() {
			this.MAX_MSG = ReameProtocol.MAX_MSG_LEN;
			this.ab = new byte[this.MAX_MSG];
			this.readLen = 0;
			this.buffer = ByteBuffer.allocate(this.MAX_MSG);
			this.msgLen = 0;
			this.totalLen =0;
			this.byteLen = 0;
			this.read = RealmeTcpServer.this.new TcpReadThread();
			this.read.setName("REAME_TCP_READ");
			this.read.start();
		}
		
		public void stopTcp() {
			try {
				this.interrupt();
				if(this.selector != null) {
					this.selector.wakeup();
				}
			} catch (Exception e) {
			}
			
			try {
				if(this.channel != null) {
					this.channel.close();
				}
			} catch (Exception e) {
			}
			
			try {
				this.selector.close();
			} catch (Exception e) {
			}
			
			this.selector = null;
			this.channel = null;
			
			try {
				if(this.read != null) {
					this.read.interrupt();
					this.read = null;
				}
			} catch (Exception e) {
			}
		}
		
		@Override
		public void run() {
			try {
				this.channel = SocketChannel.open();
				this.channel.configureBlocking(false);
				this.selector = SelectorProvider.provider().openSelector();
				this.channel.socket().setTcpNoDelay(true);
				this.channel.socket().setKeepAlive(true);
				this.channel.socket().setReceiveBufferSize(2097152);
				this.channel.socket().setSendBufferSize(1048576);
				this.remoteSocket = new InetSocketAddress(RealmeTcpServer.this.serverIp, RealmeTcpServer.this.serverPort);
				this.channel.connect(this.remoteSocket);
				this.channel.register(this.selector, 8);
				
			} catch (Exception e) {
			}
			
			label74:
			while (!interrupted()) {
				try {
					this.ready = this.selector.select();
					if(this.ready != 0) {
						Iterator ee = this.selector.selectedKeys().iterator();
						
						while (true) {
							while (true) {
								SelectionKey key;
								do {
									if(!ee.hasNext()) {
										continue label74;
									}
									
									key = (SelectionKey) ee.next();
									ee.remove();
								}while(!key.isValid());
								
								if(key.isConnectable()) {
									SocketChannel channel = (SocketChannel) key.channel();
									try {
										if(channel.isConnectionPending()) {
											channel.finishConnect();
										}
									} catch (Exception e) {
										this.interrupt();
										this.read = null;
										this.selector = null;
										RealmeTcpServer.this._business.connectFailed();
										return;
									}
									
									channel.configureBlocking(false);
									RealmeTcpServer.this.ioSession.setSocketChannel(channel);
									channel.socket().setTcpNoDelay(true);
									channel.socket().setReceiveBufferSize(2097152);
									channel.socket().setSendBufferSize(2097152);
									channel.register(this.selector, 1,RealmeTcpServer.this.ioSession);
									RealmeTcpServer.this.connectSuccess();
									RealmeTcpServer.this._business.connectSuccess(RealmeTcpServer.this.ioSession);;
									System.out.println("CONNECT_SUCCESS!");
								}else if((key.readyOps() & 1) == 1) {
									this.read(key);
								}
							}
						}
						
					}
				} catch (Exception e) {
				}
			}
			
			System.out.println("TCP THREAD STOP!!");
			
			try {
				this.read = null;
				if(this.channel != null) {
					this.channel.close();
				}
				if(this.selector != null) {
					this.selector.close();
				}
			} catch (Exception e) {
			}
		}
		
		public int read(SelectionKey sk) {
			SocketChannel sc = (SocketChannel) sk.channel();
			try {
				this.buffer.position(0);
				
				try {
					this.buffer.limit(this.MAX_MSG-this.byteLen);
				} catch (Exception e) {
					this.byteLen = 0;
					return 0;
				}
				
				this.readLen = sc.read(this.buffer);
				if(this.readLen > 0) {
					this.buffer.flip();
					this.buffer.get(this.ab,this.byteLen,this.readLen);
					this.byteLen += this.readLen;
					if(this.msgLen == 0) {
						this.msgLen = ByteTools.byte2ToShort(this.ab,1);
						this.totalLen = this.msgLen +3;
					}
					
					if(this.msgLen > this.MAX_MSG) {
						this.byteLen = 0;
						return 0;
					}
					
					if (this.totalLen > this.byteLen) {
						return 0;
					}
					
					do {
						this.ab[4] = RealmeTcpServer.this._cmd;
						this.read.addTask(this.ab, this.totalLen);
						this.msgLen = 0;
						this.byteLen -= this.totalLen;
						if(this.byteLen <=0) {
							this.byteLen = 0;
							this.totalLen = 0;
							break;
						}
						
						System.arraycopy(this.ab, this.totalLen, this.ab, 0, this.byteLen);
						if(this.byteLen < 8) {
							this.totalLen = 0;
							break;
						}
						
						this.msgLen = ByteTools.byte2ToShort(this.ab,1);
						this.totalLen = this.msgLen + 3;
						
						
					} while (this.totalLen <= this.byteLen);
				}
				
			} catch (Exception e) {
			}
		}

	}

	private String serverIp = null;
	private int serverPort = 0;
	private byte _cmd = 0;
	RealMeTcpSession ioSession = null;
	RealmeTcpServer.ClientProcess seleThread = null;
	private RealMeHandler _business;
	private ClientSendThread sendMessage = null;

	public RealmeTcpServer(byte cmd) {
		this._cmd = cmd;
		this.ioSession = new RealMeTcpSession(cmd);
	}

	public RealmeTcpServer(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	public void connect(String serverIp, int serverPort, RealMeHandler handler) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		if (this.seleThread != null) {
			this.seleThread.stopTcp();
			this.seleThread = null;
		}
		this.seleThread = new RealmeTcpServer.ClientProcess();
		this._business = _business;
		this.seleThread.start();
	}

	public void connect(RealMeHandler business) {
		this.seleThread = new RealmeTcpServer.ClientProcess();
		this._business = business;
		this.seleThread.start();
	}

	public void stopClient() {
		this.seleThread.stopTcp();
		this.seleThread = null;
	}

	public void sendToServer(int mid, int cmd, int seq, int status, int iid, byte[] body) {
		Object initBody = null;
		byte[] initBody1;
		if (body != null && body.length != 0) {
			int len = body.length + 10;
			initBody1 = new byte[len];
			System.arraycopy(body, 0, initBody1, 10, body.length);
			ByteTools.shortToByte2((short) (len - 2), initBody1, 0);
		} else {
			initBody1 = new byte[10];
			initBody1[1] = 8;
		}

		initBody1[2] = (byte) mid;
		initBody1[3] = (byte) cmd;
		initBody1[4] = (byte) status;
		initBody1[5] = (byte) seq;
		ByteTools.intToByte4(iid, initBody1, 6);
		this.sendMessage.addData(initBody1);
	}

	private void connectSuccess() {
		this.ioSession.setTsClient(this);
		if (this.sendMessage == null) {
			this.sendMessage = new ClientSendThread();
			this.sendMessage.setName("TCP_SEND");
			this.seleThread.start();
		}

		this.sendMessage.setIosession(this.ioSession);
	}

	public void sendToServer(byte[] initBody) {
		this.sendMessage.addData(initBody);
	}
}
