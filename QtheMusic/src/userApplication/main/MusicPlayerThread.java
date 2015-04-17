package userApplication.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStrea
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
			//			System.out.println("Songs in queue:");
			//			ArrayList<String> songNamesInQueue = queue.getQueueTracks();
			//			for(String songName : songNamesInQueue){
			//				System.out.println(songName);
			//			}
			//			System.out.println("Starting first song in queue");
			
			//TODO:
			//fixa så att man kan spela .wav filer också? Nu kan man istället bara spela .mp3:or
			//fixa så att mp3:orna spelas på en annan tråd, då skulle man t.ex kunna avbryta det från den tråden
			//nu köras det på denna tråden, dvs Player.play är blockerande
			
			
			int songIdToPlay = queue.getNextSongId();
			String musicFileName = songList.get(songIdToPlay);
			FileInputStream fis;
			try {
				System.out.println(folderPath+"/"+musicFileName);
				fis = new FileInputStream(folderPath+"/"+musicFileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				Player player;
				player = new Player(bis);
				MusicPlaybackThread playback = new MusicPlaybackThread(queue,player);
				playback.start();
				queue.waitForFinishedSongOrCommand();
			} catch (FileNotFoundException | JavaLayerException e2) {
				e2.printStackTrace();
			}


			//			try{
			//				File musicFile = new File(folderPath+"/"+musicFileName);
			//				AudioInputStream stream;
			//				AudioFormat format;
			//				DataLine.Info info;
			//				Clip clip;
			//				stream = AudioSystem.getAudioInputStream(musicFile);	
			//				format = stream.getFormat();
			//				info = new DataLine.Info(Clip.class, format);
			//				clip = (Clip) AudioSystem.getLine(info);
			//				clip.open(stream);
			//				clip.start();
			//				LineListener listener = new LineListener() {
			//					public void update(LineEvent event) {
			//						if (event.getType() != Type.STOP) {
			//							return;
			//						}
			//						queue.finishedSong();
			//					}
			//				};
			//				clip.addLineListener(listener);
			//			}
			//			catch (Exception e){
			//				e.printStackTrace();
			//			}
			//	queue.startingSong();
			//	queue.waitForFinishedSong();

			//some pause between the songs?
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}

}

