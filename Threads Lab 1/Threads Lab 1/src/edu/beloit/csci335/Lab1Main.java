package edu.beloit.csci335;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lab1Main {

	private Lab1Thread[] threads;
	
	public static void main(String[] args) {
		
		Lab1Main instance = new Lab1Main();
		
		if(args.length != 3) {
			System.out.println("ERROR: This program requires three arguements. Exiting now.");
			return;
		}
		
		instance.threadDemonstration(args[0], args[1], args[2]);
	}
	
	public void threadDemonstration(String name1, String name2, String name3) {

		threads = new Lab1Thread[3];
		
		threads[0] = makeThread(name1, 0.01f, 0.001f,  0.5f); 
		threads[1] = makeThread(name2, 0.9f,  0.001f,  0.5f);
		threads[2] = makeThread(name3, 0.01f, 0.0001f, 0.5f);
		
		for(Lab1Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
		
		int maxPopulationIndex = 0;
		int maxPopulation = 0;
		int maxLifetimeIndex = 0;
		int maxLifetime = 0;
		int i = 0;
		for(Lab1Thread thread : threads) {
			if(maxPopulation < thread.getMaxPopulation()) {
				maxPopulation = thread.getMaxPopulation();
				maxPopulationIndex = i;
			}
			if(maxLifetime < thread.getLifetime()) {
				maxLifetime = thread.getLifetime();
				maxLifetimeIndex = i;
			}
			i++;
		}
		
		System.out.println("The longest lived planet was " + threads[maxLifetimeIndex].getName() + ", lasting " + maxLifetime + " iterations.");
		System.out.println("The planet with the largest peak population was " + threads[maxPopulationIndex].getName() + ", with a population of " + maxPopulation + ".");
	}
	
	private Lab1Thread makeThread(String threadName, float deathFactor, float deathFactorOfSquare, float birthFactor) {
		Lab1Thread thread = new Lab1Thread(threadName, deathFactor, deathFactorOfSquare, birthFactor);
		System.out.println("Thread is named " + thread.getName());
		thread.start();
		return thread;
	}

}
