package userApplication.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import MusicQueue.ClientMusicQueue;
import userApplication.monitor.ClientMonitor;

public class ClientFromHostReaderThread extends Thread{
	private InputStream hostStream;
	private ClientMonitor clientMonitor;
	ClientMusicQueue musicQueue;
	public ClientFromHostReaderThread(InputStream hostStream,ClientMonitor clientMonitor){
		this.clientMonitor=clientMonitor;
		this.hostStream=hostStream;
	}
	public void run(){
		BufferedReader br=new BufferedReader(new InputStreamReader(hostStream));
		try {

			ArrayList<String> availableTracks=retrieveAllSongs(br);
			System.out.println("The host got the following tracks, queue as you wish.");
			musicQueue=new ClientMusicQueue(availableTracks);
			for(int i=0;i<availableTracks.size();i++){
				System.out.println("Track ID: "+i+" TrackName: "+availableTracks.get(i));
			}
			clientMonitor.setMusicQueue(musicQueue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//lägg in stopp och paus medelanden

		while(true){
			String message;
			try {
				message = br.readLine();
				String[] splittedMessage = message.split(" ");
				String command = splittedMessage[0];
				switch (command) {
				case "A":
					musicQueue.addToQueue(Integer.parseInt(splittedMessage[1]));
					break;
				case "S":
					musicQueue.startingSong();
					break;
				case "STOP":
					musicQueue.stoppingSong();
					break;
				case "F":
					musicQueue.finishedSong();
					break;	
				default:
					System.out.println("Unknown command from server: '"+message+"'");
					break;
				}


				//skriv ut nuvarande kön
				//System.out.println("nu skriver jag ut min kö");
			//	musicQueue.printQueue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}
	private ArrayList<String> retrieveAllSongs(BufferedReader br) throws IOException{
		String nbrOfSongsResponse;
		nbrOfSongsResponse=br.readLine();
		int nbrOfSongs=Integer.parseInt(nbrOfSongsResponse);
		ArrayList<String> availableTracks=new ArrayList<String>(nbrOfSongs);
		String trackName;
		for(int i=0;i<nbrOfSongs;i++){
			trackName=br.readLine();
			availableTracks.add(trackName);
		}
		return availableTracks;
	}

}
