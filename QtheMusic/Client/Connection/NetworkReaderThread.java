package Connection;

import java.io.InputStream;

public class NetworkReaderThread extends Thread {
	InputStream is;
	public NetworkReaderThread(InputStream is){
		this.is=is;
	}
	
	public void run(){
		
	}
}
