package assignment_1;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Read_Write {

	// --- Attributes 
	private Scanner myInput;

	// --- Default Constructor --- 
	public Read_Write() {
		myInput = null;
	}
	// --- Parameterized Constructor --- 
	public Read_Write(String fileName) {
		try {
			myInput = new Scanner(new FileInputStream(fileName));
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found. Please try again.");
			System.out.println("\n****** Thank you for using httpc ******");
			System.exit(0);
			}
	}
	
	// --- Methods --- 
	public String readFile() {
		String file = "";
		while(myInput.hasNextLine()) {
			file += myInput.nextLine(); 
		}
		return file;
	}
	public void closeScanner () {
		myInput.close();
	}
	
}