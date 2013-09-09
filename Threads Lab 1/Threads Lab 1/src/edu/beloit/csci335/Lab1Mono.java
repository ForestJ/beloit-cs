package edu.beloit.csci335;

public class Lab1Mono implements Runnable {

	public static final int INTERVAL_MS = 500;
	
	private float deathFactor;
	private float deathFactorOfSquare;
	private float birthFactor;
	
	public Lab1Mono(float deathFactor, float deathFactorOfSquare, float birthFactor) {
		this.deathFactor = deathFactor;
		this.deathFactorOfSquare = deathFactorOfSquare;
		this.birthFactor = birthFactor;
	}
	
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
		Lab1Mono instance = new Lab1Mono(deathFactor, deathFactorOfSquare, birthFactor);
		System.out.println("Creating thread with name " + threadName);
		Thread thread = new Thread(instance, threadName); 
		System.out.println("Thread is named " + thread.getName());
		thread.start();
	}
	
	public void run() {
		int population = 100;
		int previousPopulation = 100;
		while (population > 0) {
			int deaths = Math.round(population*population*deathFactorOfSquare);
				deaths += Math.round(population*deathFactor);
			int births = Math.round(previousPopulation+population*birthFactor);
			
			previousPopulation = population;
			population = Math.max(0, population + births - deaths);
			
			System.out.println(Thread.currentThread().getName() + ": births: " + births + " deaths: " + deaths + " population: " + population);
			
			try {
				Thread.sleep(INTERVAL_MS);
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println("Radio Transmissions from " + Thread.currentThread().getName() + " have ceased");
	}
}
