package userApplication.main;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import MusicQueue.HostMusicQueue;
import userApplication.connection.HostIDFetcherThread;
import userApplication.monitor.HostMonitor;

public class UserHost implements Runnable{
	HostMonitor monitor;
	HostMusicQueue queue;
	private String musicFolderPath;
	private String serverIp;
	private int serverPort;
	public UserHost(String serverIp,int serverPort,String musicFolderPath){
		int connectionCapacity=10;
		monitor = new HostMonitor(connectionCapacity);
		this.musicFolderPath=musicFolderPath;
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		//startTheMusicPlayer(folderPath);
	}
	public void run() {
		try {
			HostIDFetcherThread fetcher=new HostIDFetcherThread(serverIp, serverPort, monitor);
			fetcher.run();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//startTheMusicPlayer(musicFolderPath);
		// TODO Auto-generated method stub
		
	}
	/**
	 * Start the music player using music from the specified folderPath
	 * @param folderPath
	 */
	private void startTheMusicPlayer(String folderPath){
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
