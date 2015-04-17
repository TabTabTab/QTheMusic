
package userApplication.monitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import MusicQueue.Action;
import MusicQueue.HostMusicQueue;
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

	// 0 betyder ledigt, 1 upptaget eller hur är tanken?
	private boolean[] usedConnections;
	private OutputStream[] connectionStreams;
	private int numberOfAllowedClients;
	private int numberOfConnectedClients = 0;
	private HostMusicQueue songQueue;

	public HostMonitor(int numberOfAllowedClients) {
		this.numberOfAllowedClients = numberOfAllowedClients;
		usedConnections = new boolean[numberOfAllowedClients];
		connectionStreams = new OutputStream[numberOfAllowedClients];
		hostId = UNSET_HOST_ID;
		statusMessage = "";
		outBox=new ArrayList<QueueActionMessage>();
	}

	public synchronized void setMusicQueue(HostMusicQueue songQueue) {
		this.songQueue = songQueue;
	}

	public synchronized void write(String data) {
		// TODO Auto-generated method stub

	}

	/*
	 * This method will initially be called from the "readFromClient" thread
	 * when an ended connecition is detected.
	 */
	public synchronized void removeClient(int clientId) {
		// TODO:
		/* *
		 * 1: Make sure the clientId does not appear in any outBox message. (as
		 * the id is now useless)
		 * 
		 * 2: Remove the clientId from the connections list
		 * 
		 * 3: Make sure to add a message to the clientWriterThread that the id
		 * is now removed. (A add/remove connection message queue needs to be
		 * implemented)
		 * 
		 * 4: Decrement numberOfConnectedClients
		 */
	}

	/**
	 * Registers a client
	 * 
	 * @return the clientId, or -1 if the method failed
	 */
	public synchronized int addClient() {
		// TODO:
		/*
		 * 1: Find an available clientId(if available, otherwise return -1)
		 * 
		 * 2: Loop through the musicQueue and for every track, create an
		 * QueueActionMessage with the single recipient: clientId.
		 * 
		 * 3: Make sure to add a message to the clientWriterThread that the id
		 * is now added. (A add/remove connection message queue needs to be
		 * implemented)
		 * 
		 * 4: Increment numberOfConnectedClients
		 */
		return -1;
	}

	// vilken av dessa ska användas?
	// bygger upp en minimal variant för att se om jag kan skapa ett väldigt
	// simpelt system med köande av låtar.
	public synchronized int addNewClient(OutputStream outputStream) {
		int id = -1;
		for (int i = 0; i < numberOfAllowedClients; i++) {
			if (!usedConnections[i]) {
				usedConnections[i] = true;
				id = i;
				break;
			}
		}
		if (id == -1) {
			return id;
		}
		connectionStreams[id] = outputStream;
		numberOfConnectedClients++;
		sendAvailableTracksToClient(id);
		return id;

	}
	private synchronized void sendAvailableTracksToClient(int clientId){
		System.out.println("writing tracks to client");
		OutputStream clientOS=connectionStreams[clientId];
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(clientOS));
		ArrayList<String> availableTracks=songQueue.getAvailableTracks();
		writeToClient(bw,""+availableTracks.size());
		for(String track:songQueue.getAvailableTracks()){
			writeToClient(bw,track);
		}
		System.out.println("written to client");
		
	}
	private synchronized void writeToClient(BufferedWriter bw,String line){
		try {
			bw.write(line+System.lineSeparator());
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized String read() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
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

	public synchronized ServerSocket blockConnectionUntilAvailable(
			ServerSocket server) {
		int portNbr = server.getLocalPort();
		// if(numberOfConnectedClients==numberOfAllowedClients){
		// try {
		// server.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		boolean closedServer = false;
		while (numberOfConnectedClients == numberOfAllowedClients) {
			if (!closedServer) {
				try {
					server.close();
					closedServer = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (closedServer) {
			try {
				server = new ServerSocket(portNbr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server;
	}

	public synchronized SenderData getSendData() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void addAction(QueueActionMessage action) {
		int index = 0;
		for (boolean clientId : usedConnections) {
			if (clientId == false) {
				action.addRecipient(index);
			}
			index++;
		}
		outBox.add(action);
		notifyAll();
	}
	
	public synchronized void processRequest(String line, int id) {
		System.out.println("Got a request from a client: '" + line + "'");
		String[] splittedLine = line.split(" ");
		String command = splittedLine[0];
		switch (command) {
		case "Q":
			int trackIndex = Integer.parseInt(splittedLine[1]);
			songQueue.addToQueue(trackIndex);
			QueueActionMessage queueActionMessage= new QueueActionMessage(Action.ADD_TRACK,trackIndex);
			addAction(queueActionMessage);
			break;
		default:
			System.out.println("Unknown command");
			return;
		}

	}

	public synchronized void sendData() throws InterruptedException, IOException {
		while (outBox.isEmpty()) {
			wait();
		}
		for (QueueActionMessage queueMessage : outBox) {
			ArrayList<Integer> recipients = queueMessage.getRecipients();
			String message;
			switch (queueMessage.getAction()) {
			case ADD_TRACK:
				message = "Track got added to queue";
				break;
			case REMOVE_TRACK:
				message = "Track got removed";
				break;
			default:
				message = "Unsuccessfull queue message";
				break;
			}
			sendMessageToClient(message, recipients);
		}

	}

	private void sendMessageToClient(String message,
			ArrayList<Integer> recipients) throws IOException {
		for (Integer i : recipients) {
			if (connectionStreams[i] != null) {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						connectionStreams[i]));
				bw.write(message + System.lineSeparator());
				bw.flush();

			}

		}

	}

}

