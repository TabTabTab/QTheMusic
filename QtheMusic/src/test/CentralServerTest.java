package test;

import centralServer.connection.UserClientListener;
import centralServer.database.ServerMonitor;

public class CentralServerTest {

	public static void main(String[] args) {
		ServerMonitor monitor = new ServerMonitor(10);
		UserClientListener t = new UserClientListener(monitor, 8080);
		t.start();
	}
}
