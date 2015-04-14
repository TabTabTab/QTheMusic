package userApplication.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HostAddressRetriever {
	private String serverIp;
	private int serverPort;
	public HostAddressRetriever(String serverIp,int serverPort){
		this.serverIp=serverIp;
		this.serverPort=serverPort;
	}
	public InetSocketAddress retreiveHostAddress(int hostId) throws UnknownHostException, IOException, InvalidResponseException{

		Socket serverConnectionSocket=new Socket(serverIp,serverPort);
		OutputStream os=serverConnectionSocket.getOutputStream();
		InputStream is=serverConnectionSocket.getInputStream();
		makeHostAddressRequest(os,hostId);
		InetSocketAddress hostAddress=retreiveHostAddressResponse(is);
		serverConnectionSocket.close();
		return hostAddress;
	}

	private void makeHostAddressRequest(OutputStream os,int hostId) throws IOException{
		BufferedWriter br=new BufferedWriter(new OutputStreamWriter(os));
		br.write(hostId+System.lineSeparator());
		br.flush();
	}
	private InetSocketAddress retreiveHostAddressResponse(InputStream is) throws IOException,InvalidResponseException{
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		String hostAddressResponse;
		hostAddressResponse=br.readLine();
		InetSocketAddress hostAddress =createInetFromString(hostAddressResponse);
		return hostAddress;
	}
	
	private InetSocketAddress createInetFromString(String hostAddressResponse) throws InvalidResponseException{
		String[] ipAndPort=hostAddressResponse.split(":");
		if (ipAndPort.length!=2){
			String errorMsg="Response from CentralServer was not of the correct format: IP:port";
			errorMsg+=System.lineSeparator()+"Message received was: "+hostAddressResponse;
			throw new InvalidResponseException(errorMsg);
		}
		String ip=ipAndPort[0];
		try{
			int port=Integer.parseInt(ipAndPort[1].trim());
			InetSocketAddress hostAddress=new InetSocketAddress(ip, port);
			return hostAddress;
		}catch(NumberFormatException e){
			String errorMsg="Response from CentralServer did not contain a numeric port number";
			errorMsg+=System.lineSeparator()+"Message received was: "+hostAddressResponse;
			throw new InvalidResponseException(errorMsg);
		}
		
	}
}
