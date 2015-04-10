package centralServer.database;

import java.net.InetAddress;
import java.util.LinkedList;

public class ServerMonitor {
	public static final int NO_AVAILABLE_ID = -1;
	public static final String WRONG_ID = "-1"+ System.lineSeparator();
	private LinkedList<Integer> availableIDs;
	private InetAddress[] hostAddresses;

	/**
	 * Creates a Central Server Monitor, for handling multithreaded network
	 * connections through the central server. For setting up data with user
	 * hosts and user clients.
	 * 
	 * @param capacity
	 *            the maximum number of hosts the system can handle.
	 */
	public ServerMonitor(int capacity) {
		hostAddresses = new InetAddress[capacity];
		availableIDs = new LinkedList<Integer>();
		for (int i = 0; i < capacity; i++) {
			availableIDs.add(i);
		}
	}

	/**
	 * Registers a user host and gives it an ID is there are any available
	 * 
	 * @return an int representing an id if success or NO_AVAILABLE_ID if
	 *         unsuccessful
	 * 
	 */
	public synchronized int registerHost() {
		if (availableIDs.isEmpty()) {
			return NO_AVAILABLE_ID;
		} else {
			return availableIDs.removeFirst();
		}
	}

	/**
	 * Removes a host connection and makes the ID that host had available again.
	 * 
	 * @param hostId
	 *            of the host wanting to terminate the connection.
	 * @throws InvalidHostIDException
	 *             if the hostId has already been disconnected
	 */
	public synchronized void removeHost(int hostId)
			throws InvalidHostIDException {
		if (availableIDs.contains(hostId)) {
			throw new InvalidHostIDException();
		} else {
			availableIDs.add(hostId);
		}
	}

	/**
	 * Given a certain id, get the host address to this given id if it exists.
	 * If no address to the id is found a WRONG_ID package is return instead
	 * 
	 * @param id
	 *            the hostId we want to get the address for
	 * @return The address of the host as a string.
	 */
	public synchronized String getHostAddress(String id) {
		int hostId = Integer.parseInt(id);
		if (hostAddresses[hostId] == null) {
			return WRONG_ID;
		} else {
			InetAddress address = hostAddresses[hostId];
			hostAddresses[hostId] = null;
			return address.toString()+System.lineSeparator();
		}
	}

}
