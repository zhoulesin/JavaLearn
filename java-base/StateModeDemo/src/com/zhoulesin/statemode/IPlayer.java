package com.zhoulesin.statemode;

public abstract class IPlayer {
	public abstract void request(int flag);
	
	public abstract void setState(PlayerState state);
	
	public abstract void playVideo();
	
	public abstract void pause();
	
	public abstract void stop();
	
	public abstract void showAD();
}
