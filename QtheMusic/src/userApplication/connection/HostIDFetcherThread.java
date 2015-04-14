package userApplication.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import userApplication.monitor.HostMonitor;

/**
 * This thread fetches an available ID from the central server and maintains
 * connection with said server until it is time to close the
 * application/hosting. It lets the monitor handle no available ID package.
 * 
 * @author dat11sse
 * 
 */
public class HostIDFetcherThread extends Thread {

	private HostMonitor hostMonitor;
	private Socket socket;

	public HostIDFetcherThread(String address, int port, HostMonitor hostMonitor)
			throws UnknownHostException, IOException {
		this.socket = new Socket(address, port);
		this.hostMonitor = hostMonitor;

	}

	public void run() {
		boolean setUp = false;
		try {
			while (!setUp) {
				BufferedReader input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				BufferedWriter output = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream()));
				String hostAddress = hostMonitor.getHostAddress();
				output.write(hostAddress + System.lineSeparator());
				String id = input.readLine();
				// TODO: Handle no available id in monitor!
				setUp = hostMonitor.setID(id);
			}
			hostMonitor.closeConnectionToServer(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (InterruptedException e) {
		}

	}

}
