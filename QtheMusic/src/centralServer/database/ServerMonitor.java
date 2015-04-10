package centralServer.database;

import java.util.LinkedList;

public class ServerMonitor {
	private LinkedList<Integer> availableIDs;

	
	public ServerMonitor(int capacity){
		availableIDs = new LinkedList<Integer>();
		for(int i = 0; i<capacity;i++){
			
		}
	}
	
	public synchronized int registerHost(){
		return -1;
	}
}
