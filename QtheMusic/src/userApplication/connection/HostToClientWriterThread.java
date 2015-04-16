package userApplication.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import userApplication.monitor.HostMonitor;
import userApplication.monitor.SenderData;

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
			SenderData data = hostMonitor.getSendData();
			for (OutputStream os : data.destinations) {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						os));
				try {
					bw.write(data.message + System.lineSeparator());
					bw.flush();
				} catch (IOException e) {
					// TODO Handle not being able to write to outputstream in monitor or
					// elsewhere
				}
			}
		}
	}

}
