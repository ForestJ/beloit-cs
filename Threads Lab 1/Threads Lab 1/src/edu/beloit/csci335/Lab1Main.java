package edu.beloit.csci335;

public class Lab1Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// On the first day, god created PlanetA
		makeThread(args[0], 0.01f, 0.001f, 0.5f); 
		
		// On the second day, god created PlanetB
		makeThread(args[1], 0.1f, 0.001f, 0.5f);
		
		// On the third day, god created PlanetC
		makeThread(args[2], 0.01f, 0.0001f, 0.5f);

		System.out.println("main has ended.");
	}
	
	private static void makeThread(String threadName, float deathFactor, float deathFactorOfSquare, float birthFactor) {
		Lab1Thread thread = new Lab1Thread(threadName, deathFactor, deathFactorOfSquare, birthFactor);
		System.out.println("Thread is named " + thread.getName());
		thread.start();
	}

}
