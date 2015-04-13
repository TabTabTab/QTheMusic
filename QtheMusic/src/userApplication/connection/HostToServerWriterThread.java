package userApplication.connection;

import java.io.OutputStream;

public class HostToServerWriterThread extends Thread{
	OutputStream os;
	public HostToServerWriterThread(OutputStream os){
		this.os=os;
	}
	
	public void run(){
		
	}
}
