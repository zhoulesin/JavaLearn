package com.zhoulesin.statemode;

public class VideoPlayer extends IPlayer{
	private PlayerState mState = new StoppedState(this);

	@Override
	public void request(int flag) {
		mState.handle(flag);
	}

	@Override
	public void setState(PlayerState state) {
		mState = state;
	}

	@Override
	public void playVideo() {
		System.out.println("play video");
	}

	@Override
	public void pause() {
		System.out.println("pause");
	}

	@Override
	public void stop() {
		System.out.println("stop");
	}

	@Override
	public void showAD() {
		System.out.println("");
	}

}
