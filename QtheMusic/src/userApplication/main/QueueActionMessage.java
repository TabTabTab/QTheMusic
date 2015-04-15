package userApplication.main;

import java.util.ArrayList;

public class QueueActionMessage {
	public static enum Action {
		REMOVE_TRACK, ADD_TRACK
	}
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
}
