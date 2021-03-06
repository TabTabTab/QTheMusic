package userApplication.host.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import userApplication.monitor.HostMonitor;
import userApplication.musicQueue.Action;
import userApplication.musicQueue.HostMusicQueue;
import userApplication.musicQueue.PlayerCommand;
import userApplication.musicQueue.QueueActionMessage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStrea

public class MusicPlayerThread extends Thread {
	private int pausedOnFrame;
	HostMusicQueue queue;
	ArrayList<String> songList;
	String folderPath;
	HostMonitor monitor;
	public MusicPlayerThread(HostMusicQueue queue, ArrayList<String> songList,String folderPath, HostMonitor monitor) {
		this.queue = queue;
		this.songList = songList;
		this.folderPath = folderPath;
		this.monitor=monitor;
	}

	public void run() {
		while (true) {


			// TODO:
			// fixa så att man kan spela .wav filer också? Nu kan man
			// istället bara spela .mp3:or

			
			
			pausedOnFrame=0;
			
			int songIdToPlay = queue.getNextSongId();
			monitor.setCurrentlyPlayingSongID(songIdToPlay);
			QueueActionMessage queueActionMessage= new QueueActionMessage(Action.STARTED_TRACK,-1);
			monitor.addAction(queueActionMessage);
			
			String musicFileName = songList.get(songIdToPlay);
			FileInputStream fis;
			try {
				System.out.println(folderPath + "/" + musicFileName);
				fis = new FileInputStream(folderPath + "/" + musicFileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				AdvancedPlayer player;
				player = new AdvancedPlayer(bis);
				player.setPlayBackListener(new PlaybackListener() {
				    @Override
				    public void playbackFinished(PlaybackEvent event) {
				        pausedOnFrame = event.getFrame();
				    }
				});
				
				
				MusicPlaybackThread playback = new MusicPlaybackThread(queue,player,pausedOnFrame);
				playback.start();
				queue.startingSong();

				boolean finishedWithSong = false;
				PlayerCommand nextCommand;
				while(!finishedWithSong){
					PlayerCommand command = queue.waitForFinishedSongOrCommand();
					if(command==PlayerCommand.NOTHING){				
						// we recived no command, the song just finished so we exit the loop
						break;
					}
					switch (command) {
					case STOP:
						queue.finishedSong();
						queueActionMessage= new QueueActionMessage(Action.STOPPED_TRACK,-1);
						monitor.addAction(queueActionMessage);
						
						//player.stop();
						player.close();
						nextCommand = queue.waitForCommand();
						if (nextCommand == PlayerCommand.PLAY) {
							queueActionMessage= new QueueActionMessage(Action.STARTED_TRACK,-1);
							monitor.addAction(queueActionMessage);
							fis = new FileInputStream(folderPath + "/" + musicFileName);
							bis = new BufferedInputStream(fis);
							player = new AdvancedPlayer(bis);
							player.setPlayBackListener(new PlaybackListener() {
							    @Override
							    public void playbackFinished(PlaybackEvent event) {
							        pausedOnFrame = event.getFrame();
							    }
							});
							playback = new MusicPlaybackThread(queue, player,0);
							queue.startingSong();
							playback.start();
						}
						else{
							player.close();
							queue.finishedSong();
							finishedWithSong=true;
						}
						break;

					case PAUSE:
						queue.finishedSong();
						
						player.stop();
						player.close();
						System.out.println("Pausad på frame: "+pausedOnFrame);
						nextCommand = queue.waitForCommand();
						System.out.println("nästa kommando: "+nextCommand);
						System.out.println("startar sång"+queue.songIsPlaying);
						if (nextCommand == PlayerCommand.PLAY) {
							//hantera start från paus
							fis = new FileInputStream(folderPath + "/" + musicFileName);
							bis = new BufferedInputStream(fis);
							player = new AdvancedPlayer(bis);
							player.setPlayBackListener(new PlaybackListener() {
							    @Override
							    public void playbackFinished(PlaybackEvent event) {
							        pausedOnFrame = event.getFrame();
							    }
							});
							playback = new MusicPlaybackThread(queue, player,pausedOnFrame);
							queue.startingSong();
							playback.start();

						}
						else{
							player.close();
							queue.finishedSong();
							finishedWithSong=true;
						}
						break;	
					case NEXT:
						player.close();
						queue.finishedSong();
						finishedWithSong=true;
						break;
					default:
						break;
					}

					queue.setCommand(PlayerCommand.NOTHING);
				}
			} catch (FileNotFoundException | JavaLayerException e2) {
				e2.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			queueActionMessage= new QueueActionMessage(Action.FINISHED_CURRENT_TRACK,-1);
			monitor.addAction(queueActionMessage);
			monitor.setCurrentlyPlayingSongID(-1);
			
			// try{
			// File musicFile = new File(folderPath+"/"+musicFileName);
			// AudioInputStream stream;
			// AudioFormat format;
			// DataLine.Info info;
			// Clip clip;
			// stream = AudioSystem.getAudioInputStream(musicFile);
			// format = stream.getFormat();
			// info = new DataLine.Info(Clip.class, format);
			// clip = (Clip) AudioSystem.getLine(info);
			// clip.open(stream);
			// clip.start();
			// LineListener listener = new LineListener() {
			// public void update(LineEvent event) {
			// if (event.getType() != Type.STOP) {
			// return;
			// }
			// queue.finishedSong();
			// }
			// };
			// clip.addLineListener(listener);
			// }
			// catch (Exception e){
			// e.printStackTrace();
			// }
			// queue.startingSong();
			// queue.waitForFinishedSong();

			// some pause between the songs?
			//			try {
			//				Thread.sleep(2000);
			//			} catch (InterruptedException e) {
			//
			//				e.printStackTrace();
			//			}

		}

	}

}

