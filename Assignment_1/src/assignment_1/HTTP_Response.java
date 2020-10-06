package assignment_1;

import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HTTP_Response {
	
	// --- Attributes ---
	private Boolean verbose;
	private BufferedReader myInput;
	private ArrayList <String> response;
	
	// --- No Default Constructor ---
	
	// --- Parameterized Constructor ---
	public HTTP_Response (TheClientsSocket client, Boolean v) throws IOException  {
		Socket s = client.getSocket();
		this.myInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.response = new ArrayList <String>();
		this.verbose = v;
	}
	
	// --- Methods ---
	public void getResponse() throws IOException {
		String serverResponse = "";
		while (serverResponse != null) {
			serverResponse = this.myInput.readLine();
			this.response.add(serverResponse);
		}
		this.printResponse();
	} 	
	public void printResponse() throws IOException {
		int index = response.indexOf("");
		if (this.verbose)
			index = 0;
		for (int i = index; i < response.size() - 1; i++) {
			System.out.println(response.get(i));
		}

	}
	public void closeBufferedReader () throws IOException {
		this.myInput.close();
	}
	
}
