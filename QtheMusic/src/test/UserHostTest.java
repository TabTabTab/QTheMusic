package test;

import Protocol.DebugConstants;
import userApplication.main.UserHost;
import userApplication.monitor.HostMonitor;

public class UserHostTest {

	public static void main(String[] args) {
		System.out.println("hello from host");
//		ServerSocket serverSocket = new ServerSocket(30000);
//		while (true) {
//			Socket user = serverSocket.accept();
//		}
		//TODO: add functionality for letting the user specify the musicfolder
		String musicFolderPath="musik";
		UserHost userHost = new UserHost(DebugConstants.CENTRAL_SERVER_IP,DebugConstants.SERVER_HOST_PORT,musicFolderPath);
		userHost.run();
	}

}
