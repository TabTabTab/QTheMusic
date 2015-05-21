package centralServer.database;

import java.util.ArrayList;
import java.util.LinkedList;

public class ServerMonitor {
	public static final int NO_AVAILABLE_ID = -1;
	public static final String WRONG_ID = "-1";
	public static final String NO_AVAILABLE_HOSTS = "-10";
	public static final String UPDATE = "-3";
	private LinkedList<Integer> availableIDs;
	private String[] hostAddresses;
	private String message;

	/**
	 * Creates a Central Server Monitor, for handling multithreaded network
	 * connections through the central server. For setting up data with user
	 * hosts and user clients.
	 * 
	 * @param capacity
	 *            the maximum number of hosts the system can handle.
	 */

	public ServerMonitor(int capacity) {
		hostAddresses = new String[capacity];
		hostAddresses[0] = "hejsan johan och denhi:1337";
		availableIDs = new LinkedList<Integer>();
		for (int i = 1; i <= capacity; i++) {
			availableIDs.add(i);
		}
	}

	/**
	 * Registers a user host and gives it an ID is there are any available
	 * 
	 * @param hostIp
	 * @param hostPort
	 * @return an int representing an id if success or NO_AVAILABLE_ID if
	 *         unsuccessful
	 * 
	 */
	public synchronized int registerHost(String hostIp, int hostPort) {
		if (availableIDs.isEmpty()) {
			return NO_AVAILABLE_ID;
		} else {
			int hostId = availableIDs.removeFirst();
			hostAddresses[hostId - 1] = createAddressString(hostIp, hostPort);
			return hostId;
		}
	}

	private String createAddressString(String ip, int port) {
		String addressString = ip + ":" + port;
		return addressString;
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
			hostAddresses[hostId] = null;
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
		try {
			int hostId = Integer.parseInt(id);
			if(hostId > hostAddresses.length || hostId -1 < 0){
				return WRONG_ID + System.lineSeparator();
			}
			if (hostAddresses[hostId - 1] == null) {
				return WRONG_ID + System.lineSeparator();
			} else {
				String address = hostAddresses[hostId - 1];
				return address + System.lineSeparator();
			}
		} catch (NumberFormatException e) {
			return WRONG_ID + System.lineSeparator();
		}
	}

	/**
	 * Returns all ids to the currently running hosts.  
	 * @return a list of integers representing ids
	 */
	
	public synchronized ArrayList<Integer> getAllHostAddresses() {
		ArrayList<Integer> allHostAddresses = new ArrayList<Integer>();
		for (int i = 0; i < hostAddresses.length; i++) {
			if (hostAddresses[i] != null) {
				allHostAddresses.add(i+1);
			}
		}
		return allHostAddresses;
	}

	public synchronized void setMessage(String msg) {
		message = msg;
	}

	public synchronized String getMessage() {
		return message;
	}
}
