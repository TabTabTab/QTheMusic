package centralServer.database;

import java.util.LinkedList;

public class ServerMonitor {
	public static final int NO_AVAILABLE_ID = -1;
	private LinkedList<Integer> availableIDs;

	/**
	 * Creates a Central Server Monitor, for handling multithreaded network
	 * connections through the central server. For setting up data with user
	 * hosts and user clients.
	 * 
	 * @param capacity
	 *            the maximum number of hosts the system can handle.
	 */
	public ServerMonitor(int capacity) {
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
}
