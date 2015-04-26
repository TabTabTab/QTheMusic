package MusicQueue;

import java.util.ArrayList;


//bör fungera lite annorlunda än hostens, den nuvarande spelande låten borde ligga kvar här i, dvs ta bort låten när den spelats klart, inte när den börjats spela.
public class ClientMusicQueue extends MusicQueue{
	private boolean songIsPlaying = false;
	public ClientMusicQueue(ArrayList<String> availableTracks) {
		super(availableTracks);

	}

	public synchronized boolean addToQueue(int trackId){
		if(trackId>=0 && availableTracks.size()>trackId){
			trackQueue.add(trackId);
			notifyAll();
			return true;
		}else{
			return false;
		}
	}
	public synchronized void startingSong(){
		songIsPlaying=true;
	}
	public synchronized void stoppingSong(){
		songIsPlaying=false;
	}
	public synchronized void finishedSong(){
		songIsPlaying=false;
		trackQueue.remove(0);
	}

	public synchronized void printQueue(){
		boolean firstSong = true;
		for(int trackID :trackQueue){
			if(firstSong){
				if(songIsPlaying){
					System.out.println("Currently playing: "+availableTracks.get(trackID));
				}
				else{
					System.out.println(availableTracks.get(trackID));
				}
				firstSong=false;
			}
			else{
				System.out.println(availableTracks.get(trackID));
			}
		}
	}




	/**
	 * Replaces the queue with a new queue
	 * @param newQueue
	 */
	public void replaceQueue(ArrayList<Integer> newQueue){
		trackQueue=newQueue;
	}

}
