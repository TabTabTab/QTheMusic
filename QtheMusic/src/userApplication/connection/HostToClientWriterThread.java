package userApplication.connection;

import java.io.IOException;

import userApplication.monitor.HostMonitor;

/**
 * This thread gets all the message and the destinations for said message from
 * the monitor, in a wrapper class. Then it writes the message to the given
 * destinations.
 * 
 * @author dat11sse
 * 
 */
public class HostToClientWriterThread extends Thread {
	private HostMonitor hostMonitor;

	public HostToClientWriterThread(HostMonitor hostMonitor) {
		this.hostMonitor = hostMonitor;
	}

	public void run() {

		while (!isInterrupted()) {
			try {
				
				hostMonitor.sendData();
				System.out.println("nu har jag skickat data");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
