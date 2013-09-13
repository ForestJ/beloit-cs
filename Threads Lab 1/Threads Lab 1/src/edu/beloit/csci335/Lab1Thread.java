package edu.beloit.csci335;

public class Lab1Thread extends Thread {
	
	public static final int INTERVAL_MS = 200;
	
	private float deathFactor;
	private float deathFactorOfSquare;
	private float birthFactor;
	
	private int maxPopulation;
	private int lifetime;
	
	public Lab1Thread (String name, float deathFactor, float deathFactorOfSquare, float birthFactor) {
		super(name);
		this.deathFactor = deathFactor;
		this.deathFactorOfSquare = deathFactorOfSquare;
		this.birthFactor = birthFactor;
	}
	
	public int getMaxPopulation () {
		return maxPopulation;
	}
	public int getLifetime () {
		return lifetime;
	}
	
	public void run () {
		int population = 100;
		int previousPopulation = 100;
		while (population > 0) {
			int deaths = Math.round(population*population*deathFactorOfSquare);
				deaths += Math.round(population*deathFactor);
			int births = Math.round(previousPopulation+population*birthFactor);
			
			previousPopulation = population;
			population = Math.max(0, population + births - deaths);
			if(maxPopulation < population) {
				maxPopulation = population;
			}
			lifetime++;
			
			System.out.println(Thread.currentThread().getName() + ": births: " + births + " deaths: " + deaths + " population: " + population);
			
			try {
				Thread.sleep(INTERVAL_MS);
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println("Radio Transmissions from " + Thread.currentThread().getName() + " have ceased after " + lifetime + " iterations.");
	}

}
