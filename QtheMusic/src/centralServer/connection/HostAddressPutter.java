package centralServer.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import Protocol.CentralServerProtocol;
import centralServer.database.InvalidHostIDException;
import centralServer.database.ServerMonitor;

/**
 * Creates a HostAddressPutter thread, which listens to the connection made
 * after a address. If a address is found input this to the monitor and then
 * return the id mapped to the address. Finally listen to the connection to the
 * host until it dies and make the id available again
 * 
 * @author Shan
 * 
 */
public class HostAddressPutter extends Thread {
	private Socket hostClient;
	private ServerMonitor monitor;
	private static final int INVALID_PORT_NUMBER_FORMAT=-1;
	public HostAddressPutter(Socket hostClient, ServerMonitor monitor) {
		this.hostClient = hostClient;
		this.monitor = monitor;
	}

	public void run() {
		int id = ServerMonitor.NO_AVAILABLE_ID;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					hostClient.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					hostClient.getOutputStream()));
			String hostIp=hostClient.getInetAddress().getHostAddress();
			String hostPortResponse= br.readLine();
			int hostPort= portStringToInt(hostPortResponse);
			if(hostPort!=INVALID_PORT_NUMBER_FORMAT){
				id = monitor.registerHost(hostIp,hostPort);
				writeIntToClient(bw, id);
				if (id == ServerMonitor.NO_AVAILABLE_ID) {
					hostClient.close();
				}
				while (br.read() != -1);
			}else{
				writeIntToClient(bw, CentralServerProtocol.WrongData.INVALID_PORT_NUMBER_FORMAT);
			}
			hostClient.close();
		} catch (IOException e) {
			
		}
		removeAndCloseConnection(id);

	}
	private void writeIntToClient(BufferedWriter bw,int line) throws IOException{
		bw.write(line + System.lineSeparator());
		bw.flush();
	}
	/**
	 * Converts a String port to an int port number if the argument is a valid representation of a port.
	 * @param stringPort - A string containing the port number as text
	 * @return The port number as an int if the the argument was valid, otherwise NOT_VALID_PORT.
	 */
	private int portStringToInt(String stringPort){
		try{
			int port=Integer.parseInt(stringPort.trim());
			if(port>=0){
				return port;
			}
		}catch(NumberFormatException e){
		}
		return INVALID_PORT_NUMBER_FORMAT;
	}
	private void removeAndCloseConnection(int id) {
		if (id != ServerMonitor.NO_AVAILABLE_ID) {
			try {
				monitor.removeHost(id);
			} catch (InvalidHostIDException e) {
			}
		}
		try {
			hostClient.close();
		} catch (IOException e) {
		}
	}
}
