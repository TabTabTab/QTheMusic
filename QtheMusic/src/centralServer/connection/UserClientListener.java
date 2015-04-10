package centralServer.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import centralServer.database.ServerMonitor;
/**
 * Creates a UserClientListener thread, which listens to the network on a certain
 * port for a user client connection. If a connection is made create a new
 * HostAddressFetcher thread
 * 
 * @author Shan
 * 
 */
public class UserClientListener extends Thread {
	private ServerMonitor monitor;
	private int portNbr;

	public UserClientListener(ServerMonitor monitor, int portNbr) {
		this.monitor = monitor;
		this.portNbr = portNbr;
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(portNbr);
			while (!isInterrupted()) {
				try {
					Socket userClient = server.accept();
					Thread thread = new HostAddressFetcher(userClient,monitor);
					thread.start();
				} catch (IOException e) {

				}
			}
		} catch (IOException e) {
			System.err.println("Could not create a ServerSocket with portnumber " + portNbr);
			e.printStackTrace();
		}
	}

}
