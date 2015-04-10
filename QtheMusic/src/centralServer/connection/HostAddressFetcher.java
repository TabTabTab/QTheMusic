package centralServer.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import centralServer.database.ServerMonitor;

public class HostAddressFetcher extends Thread {
	private Socket userClient;
	private ServerMonitor monitor;

	public HostAddressFetcher(Socket userClient, ServerMonitor monitor) {
		this.userClient = userClient;
		this.monitor = monitor;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					userClient.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					userClient.getOutputStream()));
			String msg = br.readLine();
			msg = monitor.getHostAddress(msg);
			bw.write(msg);
			userClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
