package assignment_1;

import java.io.IOException;

public class httpc {

	public static void main(String[] args) {
		
		try {
			
			cmdParser cmd = new cmdParser();
			cmd.stringParser(args);
			
			if (cmd.getHelp())
				httpc.printHelp(cmd);
			else {
				TheClientsSocket client = new TheClientsSocket(cmd.getDomainName(), 80);
				
				HTTP_Request message = new HTTP_Request(cmd, client);
				HTTP_Response response = new HTTP_Response(client, cmd.getV());
				message.sendRequest();
				response.getResponse();
					
				client.closeSocket();
				message.closePrintStream();
				response.closeBufferedReader();
			}
		}
		catch (IOException ex) {
			//ex.printStackTrace();
			//System.out.println(ex);
			System.out.println("Something went wrong. Please try again.");
		}
		finally {
			System.out.println("\n****** Thank you for using httpc ******");
		}
	}

	public static void printHelp(cmdParser obj) {
		
		if (obj.getRequestLine().size() == 0) {
			System.out.print("httpc is a curl-like application but supports HTTP protocol only.\r\n"  + 
					"Usage:\r\n" + 
					"\thttpc command [arguments] "
					+ "\r\nThe commands are:\r\n" + 
					"\tget executes a HTTP GET request and prints the response. "
					+ "\r\n\tpost executes a HTTP POST request and prints the response. "
					+ "\r\n\thelp prints this screen.\r\n" + 
					"Use \"httpc help [command]\" for more information about a command.");
		}
		else if (obj.getRequestLine().size() != 0 && obj.getRequestLine().get(0).equals("GET")) {
			System.out.println("httpc help get\r\n" + 
					"usage: httpc get [-v] [-h key:value] URL\r\n" + 
					"Get executes a HTTP GET request for a given URL.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:value Associates headers to HTTP Request with the format 'key:value'.");
		}
		else if (obj.getRequestLine().size() != 0 && obj.getRequestLine().get(0).equals("POST")) {
			System.out.println("httpc help post\r\n" + 
					"usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n" + 
					"Post executes a HTTP POST request for a given URL with inline data or from file.\r\n" + 
					"-v Prints the detail of the response such as protocol, status, and headers.\r\n" + 
					"-h key:valueAssociates headers to HTTP Request with the format 'key:value'.\r\n" + 
					"-d stringAssociates an inline data to the body HTTP POST request.\r\n" + 
					"-f fileAssociates the content of a file to the body HTTP POST request.\r\n" + 
					"Either [-d] or [-f] can be used but not both.");
		}
	}
}


