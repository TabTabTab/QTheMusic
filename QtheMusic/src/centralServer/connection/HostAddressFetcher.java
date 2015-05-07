package centralServer.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import centralServer.database.ServerMonitor;

/**
 * Creates a HostAddressFetcher thread, which takes a user client socket and
 * reads a Id from the inputstream. Afterward calling the ServerMonitor for the
 * host address bound to this id and writes it back to the user client
 * 
 * @author Shan
 * 
 */
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
			boolean update = true;
			String msg = "";
			while (update) {
				sendAllAvailableHosts(bw);
				System.out.println("sending list to client");
				msg = br.readLine();
				System.out.println("we got this msg fro mthe  client: "+msg);
				if (!msg.equals(ServerMonitor.UPDATE)) {
					update = false;
				}
			}
			System.out.println("Clients want address for ID:'" + msg + "'");
			String address = monitor.getHostAddress(msg);
			System.out.println("that is address: " + address);
			bw.write(address);
			bw.flush();
			userClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendAllAvailableHosts(BufferedWriter bw) throws IOException,
			InterruptedException {
		ArrayList<Integer> availableHosts = monitor.getAllHostAddresses();
		StringBuilder sb = new StringBuilder();
		if (availableHosts.isEmpty()) {
			bw.write(ServerMonitor.NO_AVAILABLE_HOSTS + System.lineSeparator());
			bw.flush();
			throw new InterruptedException();
		} else {
			for (Integer i : availableHosts) {
				sb.append(i + " ");
			}
			sb.append(System.lineSeparator());
			bw.write(sb.toString());
			bw.flush();
		}
	}
}
