package assignment_1;

import java.net.Socket;
import java.io.IOException;

public class TheClientsSocket {

	// --- Attributes --- 
	private int port;
	private Socket client;
	private String domainName;
	
	// --- Default Constructor --- 
	public TheClientsSocket () throws IOException {
		this.port = 0;
		this.domainName = null;
		this.client = new Socket ();
	}
	// --- Parameterized Constructor ---
	public TheClientsSocket (String domainName, int port) throws IOException {
		this.port = port;
		this.domainName = domainName;
		this.client = new Socket(this.domainName, this.port);
	}
	// --- Getter Methods ---
	public int getPort() {
		return this.port;
	}
	public String getDomainName() {
		return this.domainName;
	}
	public Socket getSocket() {
		return this.client;
	}
	// --- Setters Methods --- 
	public void setPort(int port) {
		this.port = port;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	// --- Methods ---
	public String toString() {
		return (this.domainName + " " + this.port + "\n" + this.client);
	}
	public void closeSocket () throws IOException {
		this.client.close();
	}
	
}
			
