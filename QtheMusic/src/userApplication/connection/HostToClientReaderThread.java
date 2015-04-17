package userApplication.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import userApplication.monitor.HostMonitor;
/**
 * Thread that reads request from the client and lets the monitor process it. 
 * @author dat11sse
 *
 */
public class HostToClientReaderThread extends Thread {

	private HostMonitor hostMonitor;
	private int id;
	private Socket client;

	public HostToClientReaderThread(HostMonitor hostMonitor, Socket client,
			int id) {
		this.hostMonitor = hostMonitor;
		this.client = client;
		this.id = id;
	}

	public void run() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (!isInterrupted()) {
				String line = reader.readLine();
				hostMonitor.processRequest(line, id);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
