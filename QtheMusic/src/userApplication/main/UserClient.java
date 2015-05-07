package userApplication.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



import userApplication.connection.ClientFromHostReaderThread;
import userApplication.connection.HostAddressRetriever;
import userApplication.connection.InvalidResponseException;
import userApplication.monitor.ClientMonitor;

public class UserClient implements Runnable{
	private ClientMonitor clientMonitor;
	private String serverIp;
	private int serverPort;
	public UserClient(String serverIp,int serverPort){
		this.serverIp=serverIp;
		this.serverPort=serverPort;
		clientMonitor=new ClientMonitor();
	}


	public void run(){
//		System.out.println("What host ID do you want to connect to?");
		Scanner keyboard = new Scanner(System.in);
//		int ID = keyboard.nextInt();

		HostAddressRetriever har=new HostAddressRetriever(serverIp,serverPort);
		try {
			//TODO use an hostId defined by the user, not "1"
			InetSocketAddress hostAddress=har.retreiveHostAddress(0);
			System.out.println("We got the host address:");
			System.out.println(hostAddress);
			Socket socket = new Socket(hostAddress.getHostString(),hostAddress.getPort());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			ClientFromHostReaderThread hostListener=new ClientFromHostReaderThread(socket.getInputStream(),
					clientMonitor);
			hostListener.start();
			writer.write("list\n");
			writer.flush();
			System.out.println("Q + track id for queing, 'list' showing the queue");
			while(true){
				System.out.println("what command do you want to send");
				String line = keyboard.nextLine();
				if(line.equals("list")){
					clientMonitor.printQueue();
				}
				else{
					writer.write(line+"\n");
					writer.flush();
					System.out.println("request sent to server");
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
		}
	}

}

