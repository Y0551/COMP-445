package assignment_1;

import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cmdParser {
	
	// --- Attributes ---
	private String domainName;
	private ArrayList <String> requestLine; 
	private ArrayList <String> headerLine;
	private ArrayList <String> entityBody;
	private ArrayList <String> garbage;

	private Boolean v;
	private Boolean h;
	private Boolean d;
	private Boolean f;
	private Boolean help;
	
	// --- Default Constructor ---
	public cmdParser () {
		
		this.domainName = null;
		this.requestLine = new ArrayList <String> ();
		this.headerLine = new ArrayList <String> ();
		this.entityBody = new ArrayList <String> ();
		this.garbage = new ArrayList <String> ();
		
		this.v = false;
		this.h = false;
		this.d = false;
		this.f = false;
		this.help = false;
	}

	// --- Setter Methods ---  
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public void setRequestLine (String cmdArgument) {
		this.requestLine.add(cmdArgument);
	}
	public void setHeaderLine (String cmdArgument) {
		this.headerLine.add(cmdArgument);
	}
	public void setEntityBody (String cmdArgument) {
		this.entityBody.add(cmdArgument);
	}
	// --- Getter Methods
	public String getDomainName() {
		return this.domainName;
	}
	
	public ArrayList <String> getRequestLine() {
		return this.requestLine;
	}
	public String getRequestLine(int index) {
		if (this.requestLine.size() > index)
			return this.requestLine.get(index);
		else
			return null;		
	}
	
	public ArrayList <String> getHeaderLine() {
		return this.headerLine;
	}
	public String getHeaderLine(int index) {
		if (this.headerLine.size() > index)
			return this.headerLine.get(index);
		else
			return null;
	}
	
	public String getEntityBody() {
		return this.entityBody.toString();
	}
	public String getEntityBody(int index) {
		if (this.entityBody.size() > index)
			return this.entityBody.get(index);
		else
			return null;
	}
	
	public String getGarbage() {
		return this.garbage.toString();
	}
	public String getGarbage(int index) {
		if (this.garbage.size() > index)
			return this.garbage.get(index);
		else
			return null;
	}
	
	public Boolean getV() {
		return this.v;
	}
	public Boolean getHelp() {
		return this.help;
	}
	
	// --- Methods ---
	public void stringParser(String[] cmdInput) {

		Boolean getFlag = true;
		Boolean postFlag = true;
		
		for (int i = 0; i < cmdInput.length; i++) {

			if (cmdInput[i].toUpperCase().equals("GET")) {
				this.requestLine.add(cmdInput[i].toUpperCase());
				getFlag = false;
				continue;
			}
			if (cmdInput[i].toUpperCase().equals("POST")) {
				this.requestLine.add(cmdInput[i].toUpperCase());
				continue;
			}
			if (URLvaildater(cmdInput[i])) {
				try {
					cmdInput[i] = stringCleaner(cmdInput[i]);

					URL url = new URL(cmdInput[i]);
					this.domainName = url.getHost();
					this.requestLine.add(url.getPath());
					if (url.getQuery() != null)
						this.requestLine.add("?" + url.getQuery());
					this.headerLine.add("Host:" + url.getHost());
					continue;
				}
			catch (IOException e) {
					//System.out.println("Incorrect URL. Please try again");
				}
			}
			if (cmdInput[i].toLowerCase().equals("-v")) {
				v = true;
				continue;
			}
			if (cmdInput[i].toLowerCase().equals("-h")) {
				h = true;
				continue;
			}
			if (h == true) {
				if (headerVaildater(cmdInput[i]))
					this.headerLine.add(cmdInput[i]);	
				h = false;
				continue;
			}
			if (cmdInput[i].toLowerCase().equals("-d") && getFlag) {
				d = true;
				postFlag = false;
				continue;
			}
			if (d == true && getFlag) {
				this.entityBody.add(cmdInput[i]);
				d = false;
				continue;
			} 
			if (cmdInput[i].toLowerCase().equals("-f") && postFlag) {
				f = true;
				continue;
			}
			if (f == true && postFlag) {
				Read_Write reader = new Read_Write(cmdInput[i]);
				String s = reader.readFile();
				this.entityBody.add(s);
				reader.closeScanner();
				f = false;
				continue;
			}
			if (cmdInput[i].toLowerCase().equals("help")) {
				help = true;
				continue;
			}
			this.garbage.add(cmdInput[i]);
		}
	}
	public String toString() {
		return (this.domainName + "\n" + this.requestLine.toString() + "\n" 
				+ this.headerLine.toString() + "\n" + this.entityBody.toString());
	}
	
	// --- Helper Methods 
	private String stringCleaner(String string) {
		// A method which removes quotations both single and double
		string = string.replace("\"", "");
		string = string.replace("\'", "");
		return string;
	}
	private Boolean URLvaildater(String string) {
		// A method to validate URL's
		String regexURL = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
		Pattern pattern = Pattern.compile(regexURL);
		Matcher matcher = pattern.matcher(string);
		return matcher.find();
	}
	private Boolean headerVaildater(String string) {
		// A method to validate header input
		if (string.contains(":"))
			if(string.split(":").length == 2)
				if(string.split(":")[1].length() > 1)
					return true;
		return false;
	}
}
