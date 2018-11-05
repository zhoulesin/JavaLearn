package com.zhoulesin.statemode;

public class StoppedState extends PlayerState {

	public StoppedState(IPlayer player) {
		super(player);
	}

	@Override
	public void handle(int action) {
		switch (action) {
		case PlayerState.PLAY_OR_PAUSE:
			mPlayer.playVideo();
			mPlayer.setState(new PlayingState(mPlayer));
			break;
		default:
			break;
		}
	}

}
