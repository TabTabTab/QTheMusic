package userApplication.monitor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import MusicQueue.ClientMusicQueue;
import centralServer.database.ServerMonitor;

public class ClientMonitor implements ConnectionMonitor{
	private String hostAddress=null;
	private ClientMusicQueue musicQueue;
	public ClientMonitor(){
		musicQueue=null;
	}
	public synchronized void setMusicQueue(ClientMusicQueue musicQueue){
		this.musicQueue=musicQueue;
		notifyAll();
	}
	public synchronized void printQueue(){
		musicQueue.printQueue();
	}
	public synchronized void write(String data) {
		// TODO Auto-generated method stub
		
	}

	public synchronized String read() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Deposits a hostAddress into the monitor. The address may 
	 * not be valid but the validity of it will be checked in the getHostAddress() call.
	 * @param hostAddress - A String containing a host Address, 
	 */
	public synchronized void depositHostAddress(String hostAddress){
		this.hostAddress=hostAddress;
		notifyAll();
	}
	/**
	 * Returns an INetAddress belonging to the host, this address is gathered from the centralServer.
	 * 
	 * Waits until a hostAddress is available
	 * @return An InetAddress belonging to the host. In case something went wrong in the retrieval of the address, 
	 * null is returned.
	 * @throws InterruptedException
	 */
	public synchronized InetAddress getHostAddress() throws InterruptedException{
		while(hostAddress==null){
			wait();
		}
		if (hostAddress.equals(ServerMonitor.WRONG_ID)){
			hostAddress = null;
			return null;
		}
		InetAddress address=null;
		try {
			address = InetAddress.getByName(hostAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hostAddress = null;
		return address;
	}
}
