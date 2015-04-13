package userApplication.main;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import MusicQueue.HostMusicQueue;

public class MusicPlayer extends Thread{

	HostMusicQueue queue;
	ArrayList<String> songList;
	String folderPath;
	public MusicPlayer(HostMusicQueue queue,ArrayList<String> songList,String folderPath) {
		this.queue=queue;
		this.songList = songList;
		this.folderPath = folderPath;
	}

	public void run() {
		while(true){
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.out.println("Songs in queue:");
			ArrayList<String> songNamesInQueue = queue.getQueueTracks();
			for(String songName : songNamesInQueue){
				System.out.println(songName);
			}
			System.out.println("Starting first song in queue");
			int songIdToPlay = queue.getNextSongId();
			String musicFileName = songList.get(songIdToPlay);
			
			
			Application.launch();
			System.out.println(musicFileName);
			String bip = "file:///C:/Users/Fredrik/git/QTheMusic/QtheMusic/musik/ABBA1.mp3";
			Media hit = new Media(bip);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			
//			File file = new File(folderPath+"\\"+musicFileName);
//			Media song = new Media(file.toURI().toString());
//			MediaPlayer mediaPlayer = new MediaPlayer(song);
//			mediaPlayer.play();
//			Media song = new Media("file://"+folderPath+"/"+musicFileName);
//			MediaPlayer mediaPlayer = new MediaPlayer(song);
//			mediaPlayer.play();

			
			
			
			
		}
		
	}

}
