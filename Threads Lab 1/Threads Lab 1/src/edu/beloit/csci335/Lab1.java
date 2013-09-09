package edu.beloit.csci335;

public class Lab1 implements Runnable {

	public static final int INTERVAL_MS = 500;
	
	public Lab1() {
		
	}
	
	public static void main(String[] args) {
		String threadName = args[0];
		Lab1 instance = new Lab1();
		Thread thread = new Thread(instance, threadName); 
		thread.run();
	}
	
	public void run() {
		int population = 100;
		while (population > 0) {
			int deaths = Math.round(population*population*0.01f);
			int births = Math.round(population*0.5f);
			
			population = population + births - deaths;
			
			System.out.print("births: " + births + " deaths: " + deaths + " population: " + population);
			
			try {
				Thread.sleep(INTERVAL_MS);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
