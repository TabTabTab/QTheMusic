package userApplication.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import userApplication.monitor.HostMonitor;

public class NewClientListenerThread extends Thread{
	
	private ServerSocket server;

	public NewClientListenerThread(HostMonitor hostMonitor, int port) throws IOException{
		this.server = new ServerSocket(port);
		
	}
	
	public void run(){
		while(!Thread.currentThread().isInterrupted()){
			try {
				Socket client = server.accept();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
