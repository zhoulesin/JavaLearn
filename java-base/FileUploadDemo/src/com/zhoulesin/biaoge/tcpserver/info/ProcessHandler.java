package com.zhoulesin.biaoge.tcpserver.info;

public abstract class ProcessHandler {
	private int iid;
	private String remoteIp;
	private int port;
	private String fileId;
	private String filePath;
	private String fileType;
	private int upLoadType;
	
	public ProcessHandler() {}
	
	public abstract void notify(int state,int current,int total);
	
	public abstract int getNetwork();
	
	public abstract byte[] getPubKey();
	
	public String getRemoteIp() {
		return this.remoteIp;
	}
	
	public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getUpLoadType() {
        return this.upLoadType;
    }

    public void setUpLoadType(int upLoadType) {
        this.upLoadType = upLoadType;
    }

    public int getIid() {
        return this.iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }
}
