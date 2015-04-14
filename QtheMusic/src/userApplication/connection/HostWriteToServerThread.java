package userApplication.connection;

import java.io.OutputStream;

public class HostWriteToServerThread extends Thread{
	OutputStream os;
	public HostWriteToServerThread(OutputStream os){
		this.os=os;
	}
	
	public void run(){
		
	}
}
