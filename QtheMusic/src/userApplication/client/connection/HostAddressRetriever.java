package userApplication.client.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class HostAddressRetriever {
	private String centralServerIp;
	private int centralServerPort;

	public HostAddressRetriever(String centralServerIp, int centralServerPort) {
		this.centralServerIp = centralServerIp;
		this.centralServerPort = centralServerPort;
	}

	public InetSocketAddress retreiveHostAddress(int hostId)
			throws UnknownHostException, IOException, InvalidResponseException {

		Socket serverConnectionSocket = new Socket(centralServerIp,
				centralServerPort);
		OutputStream os = serverConnectionSocket.getOutputStream();
		InputStream is = serverConnectionSocket.getInputStream();
		boolean update = true;
		Scanner keyboard = new Scanner(System.in);
		while (update) {
			fetchAllHosts(is);
			System.out
					.println("What host ID do you want to connect to (-3 for update list)?");
			int ID = keyboard.nextInt();
			// TODO: Remove h�rdkodat shit
			System.out.println(ID);
			if (ID != -3) {
				update = false;
			}
			makeHostAddressRequest(os, ID);
		}
		InetSocketAddress hostAddress = retreiveHostAddressResponse(is);
		System.out.println("we are closing!!");
		serverConnectionSocket.close();
		return hostAddress;
	}

	private void fetchAllHosts(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String message = br.readLine();
		String[] list = message.split(" ");
		System.out.println("Available Hosts: ");
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println("-----------");

	}

	private void makeHostAddressRequest(OutputStream os, int hostId)
			throws IOException {
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os));
		br.write(hostId + System.lineSeparator());
		br.flush();
	}

	private InetSocketAddress retreiveHostAddressResponse(InputStream is)
			throws IOException, InvalidResponseException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String hostAddressResponse;
		hostAddressResponse = br.readLine();
		InetSocketAddress hostAddress = createInetFromString(hostAddressResponse);
		return hostAddress;
	}

	private InetSocketAddress createInetFromString(String hostAddressResponse)
			throws InvalidResponseException {
		String[] ipAndPort = hostAddressResponse.split(":");
		if (ipAndPort.length != 2) {
			String errorMsg = "Response from CentralServer was not of the correct format: IP:port";
			errorMsg += System.lineSeparator() + "Message received was: "
					+ hostAddressResponse;
			throw new InvalidResponseException(errorMsg);
		}
		String ip = ipAndPort[0];
		try {
			int port = Integer.parseInt(ipAndPort[1].trim());
			InetSocketAddress hostAddress = new InetSocketAddress(ip, port);
			return hostAddress;
		} catch (NumberFormatException e) {
			String errorMsg = "Response from CentralServer did not contain a numeric port number";
			errorMsg += System.lineSeparator() + "Message received was: "
					+ hostAddressResponse;
			throw new InvalidResponseException(errorMsg);
		}

	}
}
