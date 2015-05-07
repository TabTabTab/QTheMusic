package userApplication.host.main;

import userApplication.musicQueue.HostMusicQueue;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MusicPlaybackThread extends Thread{

	private AdvancedPlayer player;
	private HostMusicQueue queue;
	private int startFromFrame;

	public MusicPlaybackThread(HostMusicQueue queue, AdvancedPlayer player, int startFromFrame){
		this.queue = queue;
		this.player = player;
		this.startFromFrame = startFromFrame;
	}
	
	public void run(){
		try {
			System.out.println("Jag ska starta på: "+startFromFrame);
			player.play(startFromFrame,Integer.MAX_VALUE);
			System.out.println("nu har jag spelat klart min låt");
			queue.finishedSong();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

