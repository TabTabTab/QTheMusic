package test;

import userApplication.main.UserClient;

public class UserClientTest {
	public static void main(String[] args){
		System.out.println("hello from client");
		UserClient client=new UserClient("val-7",8080);
		client.run();
	}
}
