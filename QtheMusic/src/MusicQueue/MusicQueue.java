package MusicQueue;

import java.util.ArrayList;

public abstract class MusicQueue {
	protected ArrayList<Integer> trackQueue;
	protected ArrayList<String> availableTracks;
	/**
	 * Creates a new Host MusiqQueue
	 * @param availableTracks a list with the names of available tracks,
	 *  the index of the string is the tracks trackId
	 *
	 */
	public MusicQueue(ArrayList<String> availableTracks){
		this.availableTracks=availableTracks;
		trackQueue=new ArrayList<Integer>();
	}
	/**
	 * Fetches a list with the track names.
	 * The tracks are ordered according to the queue order.
	 * @return a list of tracks 
	 * @throws NotAvailableTrackIdException if the queue contains track ids which are not available
	 */
	public ArrayList<String> getQueueTracks(){
		ArrayList<String> tracknameQueue=new ArrayList<String>();
		for(int i:trackQueue){
			try{
				String trackName=availableTracks.get(i);
				tracknameQueue.add(trackName);
			}catch(ArrayIndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}
		return tracknameQueue;
	}
	public ArrayList<String> getAvailableTracks(){
		//TODO: maybe do not give this away?
		return availableTracks;
	}
}
