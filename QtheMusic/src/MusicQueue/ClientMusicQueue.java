package MusicQueue;

import java.util.ArrayList;

public class ClientMusicQueue extends MusicQueue{

	public ClientMusicQueue(ArrayList<String> availableTracks) {
		super(availableTracks);
	}
	/**
	 * Replaces the queue with a new queue
	 * @param newQueue
	 */
	public void replaceQueue(ArrayList<Integer> newQueue){
		trackQueue=newQueue;
	}

}
