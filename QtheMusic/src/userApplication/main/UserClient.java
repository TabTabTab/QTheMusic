package userApplication.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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
		//Scan in an Id in approriate way from user
		int dummyhostID=1;
		HostAddressRetriever har=new HostAddressRetriever(serverIp,serverPort);
		try {
			//TODO use an hostId defined by the user, not "1"
			InetSocketAddress hostAddress=har.retreiveHostAddress(dummyhostID);
			System.out.println("We got the host address:");
			System.out.println(hostAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
