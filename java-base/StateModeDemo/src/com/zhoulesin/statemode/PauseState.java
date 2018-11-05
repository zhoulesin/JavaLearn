package com.zhoulesin.statemode;

public class PauseState extends PlayerState {

	public PauseState(IPlayer player) {
		super(player);
	}

	@Override
	public void handle(int action) {
		switch (action) {
		case PlayerState.PLAY_OR_PAUSE:
			mPlayer.playVideo();
			mPlayer.setState(new PlayingState(mPlayer));
			break;
		case PlayerState.STOP:
			mPlayer.stop();
			mPlayer.setState(new StoppedState(mPlayer));
			break;
		default:
			break;
		}
	}

}
