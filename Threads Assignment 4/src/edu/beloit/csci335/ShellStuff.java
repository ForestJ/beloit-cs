package edu.beloit.csci335;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellStuff {
	public static int promptUserForInt (String prompt) {
		boolean hasValue = false;
		int value = 0;
		
		while(!hasValue) {
	        System.out.print(prompt);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	        String input = null;

	        try {
	        	input = br.readLine();
	        } catch (IOException ioException) {
	           System.out.println("IO error trying to read your name!");
	           System.out.println();
	           continue;
	        }
	        
	        try{
	        	value = Integer.parseInt(input);
			} catch (Exception exception) {
				System.out.println("Exception: input '" + input + "' cannot be parsed to an int.");
				System.out.println();
				continue;
			}
	        hasValue = true;
		}
		
		return value;
	}
}
