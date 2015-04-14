package userApplication.monitor;

import java.io.IOException;
import java.net.Socket;

public class HostMonitor implements ConnectionMonitor{

	
	private boolean closeConnection;
	public HostMonitor(){
		
	}
	public synchronized void write(String data) {
		// TODO Auto-generated method stub
		
	}

	public synchronized String read() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	public String getHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean setID(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	public void closeConnectionToServer(Socket socket) throws InterruptedException, IOException {
		
		while(!closeConnection){
			wait();
		}
		socket.close();
		
	}


}
