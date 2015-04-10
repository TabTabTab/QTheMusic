package userApplication.monitor;

public interface ConnectionMonitor {

	
	public void write(String data);
	public String read() throws InterruptedException;
}
