package edu.beloit.csci335;

public class Lab1 implements Runnable {

	public static final int INTERVAL_MS = 500;
	
	public Lab1() {
		
	}
	
	public static void main(String[] args) {
		String threadName = args[0];
		Lab1 instance = new Lab1();
		System.out.println("Creating thread with name " + threadName);
		Thread thread = new Thread(instance, threadName); 
		System.out.println("Thread is named " + thread.getName());
		thread.run();
	}
	
	public void run() {
		int population = 100;
		int previousPopulation = 100;
		while (population > 0) {
			int deaths = Math.round(population*population*0.001f);
			int births = Math.round(previousPopulation+population*0.5f);
			
			previousPopulation = population;
			population = Math.max(0, population + births - deaths);
			
			System.out.println(Thread.currentThread().getName() + ": births: " + births + " deaths: " + deaths + " population: " + population);
			
			try {
				Thread.sleep(INTERVAL_MS);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
