package userApplication.host.main;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import Protocol.DebugConstants;
import userApplication.host.connection.HostIDFetcherThread;
import userApplication.host.connection.IncomingClientListenerThread;
import userApplication.monitor.HostMonitor;
import userApplication.musicQueue.HostMusicQueue;
import userApplication.musicQueue.PlayerCommand;

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
			IncomingClientListenerThread clientListener = new IncomingClientListenerThread(monitor,DebugConstants.HOST_PORT);
			clientListener.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("Commands:\n STOP: 1 \n NEXT: 2 \n PLAY: 3\nPlease write a command pls ");
			int command = scanner.nextInt();
			// 1 == stop, 2 = next, 3 = play 4 == nothing
			switch (command){
			case 1:
				queue.setCommand(PlayerCommand.STOP);
				break;
			case 2:
				queue.setCommand(PlayerCommand.NEXT);
				break;
			case 3:
				queue.setCommand(PlayerCommand.PLAY);
				break;
			
			case 4:
				//queue.setCommand(PlayerCommand.PAUSE);
				//break;
			default:
				System.out.println("unknown command");
				break;
			}
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
		MusicPlayerThread player = new MusicPlayerThread(queue,songNames,folderPath,monitor);
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

