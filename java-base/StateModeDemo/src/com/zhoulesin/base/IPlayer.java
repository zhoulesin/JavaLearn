package com.zhoulesin.base;

public interface IPlayer {
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PAUSED = 2;
	public static final int STATE_STOPPED = 3;
	public static final int STATE_AD = 4;
	
	public void playVideo();
	
	public void pause();
	
	public void stop();
	
	//添加功能
	public void showAD();
}
