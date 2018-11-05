package com.zhoulesin.statemode;

public class PlayingState extends PlayerState {

	public PlayingState(IPlayer player) {
		super(player);
	}

	@Override
	public void handle(int action) {
		switch (action) {
		case PlayerState.PLAY_OR_PAUSE:
			mPlayer.pause();
			mPlayer.setState(new PauseState(mPlayer));
			break;
		case PlayerState.STOP:
			mPlayer.stop();
			mPlayer.setState(new StoppedState(mPlayer));
		default:
			break;
		}
	}

}
