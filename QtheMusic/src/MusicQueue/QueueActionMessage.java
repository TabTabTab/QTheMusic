package MusicQueue;

import java.util.ArrayList;

/**
 * A class representing an action to be taken upon a track in the queue.
 * The recipients which queues has to be updated according to this action is also available.
 * @author Johan
 *
 */
public class QueueActionMessage {
	
	private Action action;
	private int trackIndex;
	private ArrayList<Integer> recipients;
	
	public QueueActionMessage(Action action,int trackIndex){
		this.action=action;
		this.trackIndex=trackIndex;
		this.recipients=new ArrayList<Integer>();
	}
	public int getTrackindex(){
		return trackIndex;
	}
	public Action getAction(){
		return action;
	}
	public void addRecipient(int recipientId){
		recipients.add(recipientId);
	}
	public ArrayList<Integer> getRecipients(){
		return recipients;
	}
	public void removeRecipient(int recipientId){
		recipients.remove(recipientId);
	}
	public boolean hasNoRecipients(){
		return recipients.isEmpty();
	}
}
