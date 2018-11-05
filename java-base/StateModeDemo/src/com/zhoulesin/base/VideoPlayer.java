package com.zhoulesin.base;

public class VideoPlayer implements IPlayer {
	public int mCurrentState = STATE_STOPPED;

	@Override
	public void playVideo() {
		switch (mCurrentState) {
		case STATE_PLAYING:
			System.out.println("current state is playing,do nothing");
			break;
		case STATE_STOPPED:
		case STATE_PAUSED:
			System.out.println("play video now");
			break;
		default:
			break;
		}
		mCurrentState = STATE_PLAYING;
	}

	@Override
	public void pause() {
		switch (mCurrentState) {
		case STATE_PLAYING:
			System.out.println("pause video now");
			break;
		case STATE_PAUSED:
			System.out.println("current state is paused,do nothing");
			break;
		case STATE_STOPPED:
			System.out.println("current state is stopped,do nothing");
			break;
		default:
			break;
		}
		mCurrentState = STATE_PAUSED;
	}

	@Override
	public void stop() {
		switch (mCurrentState) {
		case STATE_PAUSED:
		case STATE_PLAYING:
			System.out.println("stop video now");
			break;

		case STATE_STOPPED:
			System.out.println("current state is stopped,do nothing");
			break;

		default:
			break;
		}
		mCurrentState = STATE_STOPPED;
	}

	@Override
	public void showAD() {
		switch (mCurrentState) {
		case STATE_AD:
			System.out.println("current state is AD,do nothing");
			break;
		case STATE_PLAYING:
			System.out.println("show ad now");
			break;
		case STATE_PAUSED:
			System.out.println("current state is paused,do nothing");
			break;
		case STATE_STOPPED:
			System.out.println("current state is stopped,do nothing");
			break;
		default:
			break;
		}
	}

}
