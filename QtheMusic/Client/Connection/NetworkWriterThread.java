package Connection;

import java.io.OutputStream;

public class NetworkWriterThread extends Thread{
	OutputStream os;
	public NetworkWriterThread(OutputStream os){
		this.os=os;
	}
	
	public void run(){
		
	}
}
