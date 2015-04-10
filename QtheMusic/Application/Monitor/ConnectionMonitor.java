package Monitor;

public interface ConnectionMonitor {

	
	public void write(byte[] data);
	public byte[] read() throws InterruptedException;
}
