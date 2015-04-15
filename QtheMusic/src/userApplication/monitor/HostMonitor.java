package userApplication.monitor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import userApplication.main.QueueActionMessage;
import Protocol.CentralServerProtocol;
import centralServer.database.ServerMonitor;

public class HostMonitor implements ConnectionMonitor {

	private boolean closeConnection;
	private boolean serverConnectionIsAlive;
	private int hostId;
	private String statusMessage;
	private static final int UNSET_HOST_ID = -1;
	private ArrayList<QueueActionMessage> outbox;
	private int[] connections;
	public HostMonitor(int numberOfConnections) {
		connections=new int[numberOfConnections];
		hostId = UNSET_HOST_ID;
		statusMessage = "";
	}

	public synchronized void write(String data) {
		// TODO Auto-generated method stub

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
		outbox.add(action);
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

	public synchronized void addNewClient(OutputStream outputStream) {
		// TODO Auto-generated method stub

	}

	public synchronized ServerSocket blockConnectionUntilAvailable(
			ServerSocket server) {
		int portNbr = server.getLocalPort();
		// TODO block until client spots are available in the monitor. When
		// blocking close the server, reopen the server before returning (new
		// ServerSocket).
		return null;
	}

}
