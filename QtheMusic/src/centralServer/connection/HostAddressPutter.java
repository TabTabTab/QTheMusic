package centralServer.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import centralServer.database.InvalidHostIDException;
import centralServer.database.ServerMonitor;

/**
 * Creates a HostAddressPutter thread, which listens to the connection made
 * after a address. If a address is found input this to the monitor and then
 * return the id mapped to the address. Finally listen to the connection to the
 * host until it dies and make the id available again
 * 
 * @author Shan
 * 
 */
public class HostAddressPutter extends Thread {
	private Socket hostClient;
	private ServerMonitor monitor;

	public HostAddressPutter(Socket hostClient, ServerMonitor monitor) {
		this.hostClient = hostClient;
		this.monitor = monitor;
	}

	public void run() {
		int id = ServerMonitor.NO_AVAILABLE_ID;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					hostClient.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					hostClient.getOutputStream()));
			String address = br.readLine();
			id = monitor.registerHost(address);
			bw.write(id + System.lineSeparator());
			bw.flush();
			if (id == ServerMonitor.NO_AVAILABLE_ID) {
				hostClient.close();
			}
			while (br.read() != -1)
				;
		} catch (IOException e) {

		}
		removeAndCloseConnection(id);

	}

	private void removeAndCloseConnection(int id) {
		if (id != ServerMonitor.NO_AVAILABLE_ID) {
			try {
				monitor.removeHost(id);
			} catch (InvalidHostIDException e) {
			}
		}
		try {
			hostClient.close();
		} catch (IOException e) {
		}
	}
}
