package userApplication.monitor;

public interface ConnectionMonitor {

	
	public void write(byte[] data);
	public byte[] read() throws InterruptedException;
}
