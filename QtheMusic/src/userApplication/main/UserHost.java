package userApplication.main;

import java.io.File;
import java.util.ArrayList;

import MusicQueue.HostMusicQueue;
import userApplication.monitor.HostMonitor;

public class UserHost {
	HostMonitor monitor;
	HostMusicQueue queue;
	public UserHost(String folderPath){
		monitor = new HostMonitor();
		
		ArrayList<String> songNames = getMusicFileNames(folderPath);
		ArrayList<String> temp = getMusicFileNames(folderPath);
		queue = new HostMusicQueue(temp);
		
		queue.addToQueue(0);
		queue.addToQueue(1);
		MusicPlayerThread player = new MusicPlayerThread(queue,songNames,folderPath);
		player.start();
		try {
			Thread.sleep(1000*60*10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue.addToQueue(0);
		queue.addToQueue(1);
	}
	
	/**
	 * Creates a list of the music files in the folder and returns it.
	 * @param folderPath 
	 * @return
	 */
	ArrayList<String> getMusicFileNames(String folderPath){
		ArrayList<String> musicFileNames = new ArrayList<String>();
		File folder = new File(folderPath);
		String musicFilename;
		for(File file : folder.listFiles()){
			musicFilename=file.getName();
			if(musicFilename.endsWith(".wav")){
				musicFileNames.add(musicFilename);
			}
		}
		return musicFileNames;
	}
}
