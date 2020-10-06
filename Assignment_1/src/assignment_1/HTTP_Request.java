package assignment_1;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;

public class HTTP_Request {

	// --- Attributes ---
	private Boolean verbose;
	private String requestLine;
    private String headerLine;
    private String entityBody;
	private PrintStream myOutput; 
    
	
	// --- Default Constructor ---
	public HTTP_Request () {
		this.verbose = null;
		this.requestLine = "";
		this.headerLine = "";
		this.entityBody = "";
		this.myOutput = null;
	}
	// --- Parameterized Constructor ---
	public HTTP_Request(cmdParser obj, TheClientsSocket client) throws IOException {
		verbose = obj.getV();
		this.requestLine = "";
		this.headerLine = "";
		this.entityBody = "";
		this.setRequestLine(obj);
		this.setEntityBody(obj);
		this.setHeaderLine(obj);
		Socket s = client.getSocket();
		this.myOutput = new PrintStream(s.getOutputStream());
	}
	
	// --- Setter Methods ---
	public void setVerbose (Boolean v) {
		this.verbose = v;
	}	
	private void setRequestLine(cmdParser obj) {
		for(int i = 0; i < obj.getRequestLine().size(); i++) {
			if (i == 0)
				this.requestLine += obj.getRequestLine().get(i) + " ";
			else 
				this.requestLine += obj.getRequestLine().get(i);
			
		}
		this.requestLine += " HTTP/1.0\r\n";
	}
	private void setHeaderLine(cmdParser obj) {
		this.findReplace(obj,"User-Agent:httpc/1.1");
		this.findReplace(obj,"Accept:*/*");
		for(int i = 0; i < obj.getHeaderLine().size(); i++) {	
			this.headerLine += (formatHeader(obj.getHeaderLine(i)));
		}
		this.headerLine += "\r\n";
	}
	private void setEntityBody(cmdParser obj) {
		// Create a for loop for multiple -d (inputs)
		if (!obj.getEntityBody().equals("[]")) {
			this.entityBody = obj.getEntityBody(0);
			obj.setHeaderLine("Content-Length:"+ obj.getEntityBody(0).length());
		}
	}
	
	// --- Methods ---
	public String toString() {
		return (this.requestLine + this.headerLine + this.entityBody);
	} 
	public void sendRequest() {
		this.myOutput.print(this);
		this.printRequest(this.verbose);
	}
	public void printRequest(Boolean v) {
		if (v)
			System.out.println(this.requestLine + this.headerLine);
	}
	public void closePrintStream () throws IOException {
		this.myOutput.close();
	}
	
	// --- Helper Methods 
	private String formatHeader (String headerInfo) {
		String head = headerInfo.split(":")[0].trim();
		String tail = headerInfo.split(":")[1].trim();
		return (head +": "+ tail + "\r\n");
	}
	private void findReplace(cmdParser obj, String s) {
		String s1 = "";
		Boolean flag = false; 
		String s2 = s.split(":")[0].trim();
		for (int i = 0; i < obj.getHeaderLine().size(); i++) {
			s1 = obj.getHeaderLine().get(i).split(":")[0];
			if (s1.equalsIgnoreCase(s2)) {
				flag = true;
				break;
			}
		}
		if (flag == false)
			obj.getHeaderLine().add(s);
	}
}
