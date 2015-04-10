package connection;

import java.io.InputStream;

import monitor.ConnectionMonitor;

public class NetworkReaderThread extends Thread {
	InputStream is;
	ConnectionMonitor monitor;
	public NetworkReaderThread(InputStream is, ConnectionMonitor monitor){
		this.is=is;
		this.monitor=monitor;
	}
	
	public void run(){
		
	}
}
