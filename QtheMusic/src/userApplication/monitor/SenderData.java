package userApplication.monitor;

import java.io.OutputStream;

/**
 * SenderData is a class that contains information about where and what the
 * sender thread should send data to.
 * Right now just a test class
 * @author dat11sse
 * 
 */
public class SenderData {
	public String message;
	public OutputStream[] destinations;

	public SenderData(String message, OutputStream[] destinations) {
		this.message = message;
		this.destinations = destinations;
	}


}
