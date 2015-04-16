package userApplication.monitor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import MusicQueue.QueueActionMessage;
import Protocol.CentralServerProtocol;
import centralServer.database.ServerMonitor;

public class HostMonitor implements ConnectionMonitor {

	private boolean closeConnection;
	private boolean serverConnectionIsAlive;
	private int hostId;
	private String statusMessage;
	private static final int UNSET_HOST_ID = -1;
	private ArrayList<QueueActionMessage> outBox;
	private int[] connections;
	private int numberOfAllowedClients;
	private int numberOfConnectedClients = 0;
	public HostMonitor(int numberOfAllowedClients) {
		this.numberOfAllowedClients=numberOfAllowedClients;
		connections=new int[numberOfAllowedClients];
		hostId = UNSET_HOST_ID;
		statusMessage = "";
	}

	public synchronized void write(String data) {
		// TODO Auto-generated method stub

	}

	/*
	 * This method will initially be called from the "readFromClient" 
	 * thread when an ended connecition is detected.
	 */
	public synchronized void removeClient(int clientId){
		//TODO:
		/*			 * 
		 * 1:	Make sure the clientId does not appear in any
		 * 		outBox message. (as the id is now useless)
		 * 
		 * 2:	Remove the clientId from the connections list
		 * 
		 * 3:	Make sure to add a message to the clientWriterThread that the id is now removed.
		 * 		(A add/remove connection message queue needs to be implemented)
		 * 
		 * 4:	Decrement numberOfConnectedClients
		 */
	}
	/**
	 * Registers a client
	 * @return the clientId, or -1 if the method failed
	 */
	public synchronized int addClient(){
		//TODO:
		/*
		 * 1:	Find an available clientId(if available, otherwise return -1)
		 * 
		 * 2:	Loop through the musicQueue and for every track, create an QueueActionMessage
		 * 		with the single recipient: clientId.
		 * 
		 * 3:	Make sure to add a message to the clientWriterThread that the id is now added.
		 * 		(A add/remove connection message queue needs to be implemented)	
		 * 
		 * 4:	Increment numberOfConnectedClients
		 */
		return -1;
	}

	public synchronized String read() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	public synchronized void addAction(QueueActionMessage action){
		for(int clientId:connections){
			if(clientId!=0){
				action.addRecipient(clientId);
			}
		}
		outBox.add(action);
	}
	public synchronized String getHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized boolean setID(int hostId) {
		if (hostId == ServerMonitor.NO_AVAILABLE_ID) {
			hostId = UNSET_HOST_ID;
			statusMessage = "No ID is currently available";
		} else if (hostId == CentralServerProtocol.WrongData.INVALID_PORT_NUMBER_FORMAT) {
			System.err
			.println("Invalid port number format message received from centralserver during setup");
			hostId = UNSET_HOST_ID;
		} else {
			this.hostId = hostId;
			statusMessage = "No ID is currently available";
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void waitForServerConnectionToClose(Socket socket)
			throws InterruptedException, IOException {

		while (!closeConnection) {
			wait();
		}
		socket.close();
	}

	public synchronized void setCentralServerAliveStatus(
			boolean serverConnectionIsAlive) {
		// TODO: handle this in some way
		this.serverConnectionIsAlive = serverConnectionIsAlive;
		notifyAll();
	}

	public synchronized int addNewClient(OutputStream outputStream) {
		// TODO Auto-generated method stub
		return 0;

	}

	public synchronized ServerSocket blockConnectionUntilAvailable (ServerSocket server) {
		int portNbr = server.getLocalPort();
		if(numberOfConnectedClients==numberOfAllowedClients){
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while(numberOfConnectedClients==numberOfAllowedClients){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			server = new ServerSocket(portNbr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
	}

	public synchronized SenderData getSendData() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void processRequest(String line, int id) {
		// TODO Auto-generated method stub

	}


}
