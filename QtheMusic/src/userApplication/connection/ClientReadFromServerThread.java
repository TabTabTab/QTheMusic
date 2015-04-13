package userApplication.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import userApplication.monitor.ClientMonitor;
import userApplication.monitor.ConnectionMonitor;
/**
 * Reads from the specific InputStream specified, and deposits the first response in 
 * the given ClientMonitor as a possible hostAddress.
 * No validation is made from this thread, validation will be made inside the monitor.
 * @author Johan
 *
 */
public class ClientReadFromServerThread extends Thread {
	InputStream is;
	ClientMonitor monitor;
	public ClientReadFromServerThread(InputStream is, ClientMonitor monitor){
		this.is=is;
		this.monitor=monitor;
	}
	public void run(){
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		try {
			String hostAddress=br.readLine();
			monitor.depositHostAddress(hostAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
