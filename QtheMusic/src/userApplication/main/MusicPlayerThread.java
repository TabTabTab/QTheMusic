package userApplication.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;




import MusicQueue.HostMusicQueue;

public class MusicPlayerThread extends Thread{

	HostMusicQueue queue;
	ArrayList<String> songList;
	String folderPath;
	public MusicPlayerThread(HostMusicQueue queue,ArrayList<String> songList,String folderPath) {
		this.queue=queue;
		this.songList = songList;
		this.folderPath = folderPath;
	}

	public void run() {
		while(true){
			System.out.println("Songs in queue:");
			ArrayList<String> songNamesInQueue = queue.getQueueTracks();
			for(String songName : songNamesInQueue){
				System.out.println(songName);
			}
			System.out.println("Starting first song in queue");
			int songIdToPlay = queue.getNextSongId();
			String musicFileName = songList.get(songIdToPlay);


			InputStream in;
			
				try {
					in = new FileInputStream(folderPath+"/"+musicFileName);
					AudioStream audioStream = new AudioStream(in);
					AudioPlayer.player.start(audioStream);
					AudioPlayer.player.join();
				} catch (IOException e) {

					e.printStackTrace();
				} catch (InterruptedException e) {

					e.printStackTrace();
				}



		}

	}

}
