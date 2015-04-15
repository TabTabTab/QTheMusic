package userApplication.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import userApplication.monitor.HostMonitor;
/**
 * UNDONE
 * @author Shan
 *
 */
public class NewClientListenerThread extends Thread {
	
	
	
	private ServerSocket server;
	private HostMonitor hostMonitor;

	public NewClientListenerThread(HostMonitor hostMonitor, int portNbr)
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
					hostMonitor.addNewClient(client.getOutputStream());
					HostToClientReaderThread reader = new HostToClientReaderThread(
							hostMonitor, client);
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
