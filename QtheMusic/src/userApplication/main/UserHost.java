package userApplication.main;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import MusicQueue.HostMusicQueue;
import Protocol.DebugConstants;
import userApplication.connection.HostIDFetcherThread;
import userApplication.connection.NewClientListenerThread;
import userApplication.monitor.HostMonitor;

public class UserHost implements Runnable{
	HostMonitor monitor;
	HostMusicQueue queue;
	private String musicFolderPath;
	private String serverIp;
	private int serverPort;
	public UserHost(String serverIp,int serverPort,String musicFolderPath){
		monitor = new HostMonitor(DebugConstants.MAX_NBR_OF_CLIENTS_ALLOWED_BY_HOST);
		this.musicFolderPath=musicFolderPath;
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		//startTheMusicPlayer(folderPath);
	}
	public void run() {
		try {
			HostIDFetcherThread fetcher=new HostIDFetcherThread(serverIp, serverPort, monitor);
			fetcher.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startTheMusicPlayer(musicFolderPath);
		try {
			NewClientListenerThread clientListener = new NewClientListenerThread(monitor,DebugConstants.HOST_PORT);
			clientListener.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Start the music player using music from the specified folderPath
	 * @param folderPath
	 */
	private void startTheMusicPlayer(String folderPath){
		ArrayList<String> songNames = getMusicFileNames(folderPath);
		ArrayList<String> temp = getMusicFileNames(folderPath);
		queue = new HostMusicQueue(temp);
		monitor.setMusicQueue(queue);
		MusicPlayerThread player = new MusicPlayerThread(queue,songNames,folderPath);
		player.start();
	}
	/**
	 * Creates a list of the music files in the folder and returns it.
	 * @param folderPath 
	 * @return
	 */
	private ArrayList<String> getMusicFileNames(String folderPath){
		ArrayList<String> musicFileNames = new ArrayList<String>();
		File folder = new File(folderPath);
		String musicFilename;
		for(File file : folder.listFiles()){
			musicFilename=file.getName();
			if(musicFilename.endsWith(".mp3")){
				musicFileNames.add(musicFilename);
			}
		}
		return musicFileNames;
	}
}
