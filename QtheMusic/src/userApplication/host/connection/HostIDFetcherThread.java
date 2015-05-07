package userApplication.host.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Protocol.DebugConstants;
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

	public HostIDFetcherThread(String serverIP, int serverPort, HostMonitor hostMonitor)
			throws UnknownHostException, IOException {
		this.socket = new Socket(serverIP, serverPort);
		this.hostMonitor = hostMonitor;
	}
	
	public void run() {
		boolean setUp = false;
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			int hostPort=DebugConstants.HOST_PORT;
			output.write(hostPort + System.lineSeparator());
			output.flush();
			System.out.println("waiting for id response from server");
			String idResponse = input.readLine();
			System.out.println("Host got id response: '"+idResponse+"'");
			// TODO: Handle no available id in monitor!
			int id=Integer.parseInt(idResponse);
			setUp = hostMonitor.setID(id);
			if(setUp){
				hostMonitor.waitForServerConnectionToClose(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (InterruptedException e) {
		}
		hostMonitor.setCentralServerAliveStatus(false);
	}
}
