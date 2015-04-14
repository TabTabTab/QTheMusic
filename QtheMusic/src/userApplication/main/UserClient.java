package userApplication.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import userApplication.connection.ClientReadFromServerThread;
import userApplication.connection.HostWriteToServerThread;
import userApplication.monitor.ClientMonitor;

public class UserClient {
	private ClientMonitor clientMonitor;
	private Socket serverConnectionSocket;
	public UserClient(){
		clientMonitor=new ClientMonitor();
	}
	/**
	 * Sets up the connection between the Client and the central server. 
	 * This needs to be done in order to fetch the Hosts address via a hostID
	 * @param serverIp
	 * @param serverpPort
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void setUpCentrailServerConnection(String serverIp,int serverpPort) throws UnknownHostException, IOException{
		//TODO this method may need to be slipt up or redefined, it is not yet finished
		serverConnectionSocket=new Socket(serverIp,serverpPort);
		OutputStream os=serverConnectionSocket.getOutputStream();
		InputStream is=serverConnectionSocket.getInputStream();
		ClientReadFromServerThread serverReaderThread=new ClientReadFromServerThread(is, clientMonitor);
		HostWriteToServerThread serverWriterrThread=new HostWriteToServerThread(os);
		
		//TODO start the treads etc. 
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
