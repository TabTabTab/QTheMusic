package MusicQueue;

import java.util.ArrayList;

public class HostMusicQueue extends MusicQueue {
	private String folderPath;
	private boolean songIsPlaying = false;
	/**
	 * Creates a new Host MusiqQueue
	 * @param availableTracks a list with the names of available tracks,
	 *  the index of the string is the tracks trackId
	 *
	 */
	public HostMusicQueue(ArrayList<String> availableTracks){
		super(availableTracks);
	}
	/**
	 * Removes a track in the queue at the specified position
	 * @param queueIndex - the specified queue position of the track to be removed 
	 * @return true if the track was successfully removed, otherwise false
	 */
	public synchronized boolean removeAtIndex(int queueIndex){
		if(queueIndex>=0 && trackQueue.size()>queueIndex){
			trackQueue.remove(queueIndex);
			return true;
		}
		return false;
	}
	/**
	 * Removes the first track in the queue
	 * @return true if the track was successfully removed, otherwise false
	 */
	public synchronized boolean removeFirst(){
		if (!trackQueue.isEmpty()){
			trackQueue.remove(0);
			return true;
		}
		return false;
	}
	/**
	 * gets the next song id to play, if there is no song the thread will sleep. When sucessfull the songID will be removed from the queue
	 * @return
	 */
	public synchronized int getNextSongId(){
		while(trackQueue.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return trackQueue.remove(0);
	}
	
	
	
	/**
	 * Adds the track with the specified trackId to the queue
	 * @param trackId - the id of the track to be added
	 * @return true if the track Id existed, otherwise false
	 */
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
		songIsPlaying = true;
	}
	public synchronized void finishedSong(){
		songIsPlaying = false;
		notifyAll();
	}
	/**
	 * method for the music playing thread to wait for the song to finish
	 */
	public synchronized void waitForFinishedSong(){
		while(songIsPlaying){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
