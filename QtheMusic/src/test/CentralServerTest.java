package test;

import Protocol.DebugConstants;
import sun.security.krb5.internal.HostAddress;
import centralServer.connection.UserClientListener;
import centralServer.connection.UserHostListener;
import centralServer.database.ServerMonitor;

public class CentralServerTest {

	public static void main(String[] args) {
		System.out.println("hello from Central Server");
		ServerMonitor monitor = new ServerMonitor(10);
		UserClientListener ucl = new UserClientListener(monitor, DebugConstants.SERVER_CLIENT_PORT);
		ucl.start();
		UserHostListener uhl=new UserHostListener(monitor, DebugConstants.SERVER_HOST_PORT);
		uhl.start();
	}
}
