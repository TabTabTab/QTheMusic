package centralServer.main;

import Protocol.DebugConstants;
import centralServer.connection.UserClientListener;
import centralServer.connection.UserHostListener;
import centralServer.database.ServerMonitor;

public class CentralServer {
	private ServerMonitor monitor;
	public CentralServer(int maxNumberOfHosts){
		monitor = new ServerMonitor(maxNumberOfHosts);
		
	}
	public void run(){
		UserClientListener ucl = new UserClientListener(monitor, DebugConstants.SERVER_CLIENT_PORT);
		ucl.start();
		
		UserHostListener uhl=new UserHostListener(monitor, DebugConstants.SERVER_HOST_PORT);
		uhl.start();
	}
}
