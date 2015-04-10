package userApplication.main;

import userApplication.monitor.ClientMonitor;

public class UserClient {
	private ClientMonitor clientMonitor;
	public UserClient(){
		clientMonitor=new ClientMonitor();
	}
	/**
	 * Connects the UserClient to a host
	 * @param hostId - The id of the host to connect to.
	 * @return true if a connection was successfully established, otherwise false.
	 */
	public boolean connectToHost(int hostId){
		
		//TODO connect to the client
		return false;
	}
}
