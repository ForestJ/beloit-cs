package edu.beloit.csci335;

import java.util.Random;

public class SerialBasic {
	
	class valuePrompt {
		String prompt = "";
	}
	
	public static void main(String[] args) {
		
		int randomSeed = ShellStuff.promptUserForInt("Please enter a random seed: ");
		int sumThisManyNumbers = ShellStuff.promptUserForInt("How many numbers do you want to sum? ");
		
		count(randomSeed, sumThisManyNumbers);

	}
	
	private static void count(int randomSeed, int sumThisManyNumbers) {
		Random random = new Random();
		random.setSeed(randomSeed);
		
		System.out.println("as " + sumThisManyNumbers);
	}
	
	
}
