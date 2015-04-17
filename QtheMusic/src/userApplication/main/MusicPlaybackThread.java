package userApplication.main;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import MusicQueue.HostMusicQueue;

public class MusicPlaybackThread extends Thread{

	private Player player;
	private HostMusicQueue queue;

	public MusicPlaybackThread(HostMusicQueue queue, Player player){
		this.queue = queue;
		this.player = player;
	}
	
	public void run(){
		try {
			queue.startingSong();
			player.play();
			queue.finishedSong();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
