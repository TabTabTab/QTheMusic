package userApplication.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import userApplication.monitor.HostMonitor;

/**
 * Creates a NewClientListenerThread, which is a Thread that creates a
 * serversocket given a portnumber. This thread listens for new connections and
 * creates new HostToclientReader threads for each new connection. This thread
 * will listen for new connections as long as the monitor says it is okay,
 * (through the use of the method blockedConnectionUntilAvailable).
 * 
 * @author Shan
 * 
 */
public class IncomingClientListenerThread extends Thread {

	private ServerSocket server;
	private HostMonitor hostMonitor;

	public IncomingClientListenerThread(HostMonitor hostMonitor, int portNbr)
			throws IOException {
		this.server = new ServerSocket(portNbr);
		this.hostMonitor = hostMonitor;

	}

	public void run() {
		HostToClientWriterThread writer = new HostToClientWriterThread(
				hostMonitor);
		writer.start();
		while (!Thread.currentThread().isInterrupted()) {
			server = hostMonitor.blockConnectionUntilAvailable(server);
			try {
				Socket client = server.accept();
				int id = hostMonitor.addNewClient(client.getOutputStream());
				HostToClientReaderThread reader = new HostToClientReaderThread(
						hostMonitor, client, id);
				reader.start();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
