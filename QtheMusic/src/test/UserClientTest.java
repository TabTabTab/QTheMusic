package test;

import Protocol.DebugConstants;
import userApplication.client.main.UserClient;

public class UserClientTest {
	public static void main(String[] args){
		System.out.println("hello from client");
		UserClient client=new UserClient(DebugConstants.CENTRAL_SERVER_IP,DebugConstants.SERVER_CLIENT_PORT);
		client.run();
	}
}
