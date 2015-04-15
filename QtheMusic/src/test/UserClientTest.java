package test;

import Protocol.DebugConstants;
import userApplication.main.UserClient;

public class UserClientTest {
	public static void main(String[] args){
		System.out.println("hello from client");
		UserClient client=new UserClient("localhost",DebugConstants.SERVER_CLIENT_PORT);
		client.run();
	}
}
