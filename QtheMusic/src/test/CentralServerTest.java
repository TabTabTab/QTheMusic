package test;

import Protocol.DebugConstants;
import centralServer.main.CentralServer;

public class CentralServerTest {
	public static void main(String[] args) {
		System.out.println("hello from Central Server");
		CentralServer centralServer = new CentralServer(DebugConstants.MAX_NBR_OF_HOSTS_ALLOWED_BY_SERVER);
		centralServer.run();
	}
}
