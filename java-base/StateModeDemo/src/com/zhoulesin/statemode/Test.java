package com.zhoulesin.statemode;

public class Test {
	public static void main(String[] args) {
		IPlayer player = new VideoPlayer();
		player.request(PlayerState.PLAY_OR_PAUSE);
		player.request(PlayerState.PLAY_OR_PAUSE);
		player.request(PlayerState.PLAY_OR_PAUSE);
		player.request(PlayerState.PLAY_OR_PAUSE);
		player.request(PlayerState.STOP);
		player.request(PlayerState.STOP);
		player.request(PlayerState.PLAY_OR_PAUSE);
	}
}
