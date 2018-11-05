package com.zhoulesin.base;

public class Test {
	public static void main(String[] args) {
		
		IPlayer player = new VideoPlayer();
		player.playVideo();
		player.playVideo();
		player.showAD();
		player.pause();
		player.pause();
		player.showAD();
		player.stop();
		player.pause();
		player.showAD();
	}
}
