package edu.beloit.csci335;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterAppParallel extends CounterAppSerial {
	
	public static Object lock = new Object();
	public static CyclicBarrier barrier;
	public static int nextIndex = 0;
	public static Float linearResult = new Float(0);
	public static Float permutedResult = new Float(0);
	public static boolean finishedPermuted = false;
	public static Float prefixResult = new Float(0);
	public static CounterAppParallel instance;
	
	public static void main(String[] args) {
		int seed = ShellStuff.promptUserForInt("Please enter a random seed: ");
		howManyNumbers = ShellStuff.promptUserForInt("How many numbers do you want to sum? ");
		
		permutation = PermutationHelper.generateRandomPermutation(seed, howManyNumbers);
		values = getRandomFloats(GEN_VALUES_SEED, howManyNumbers);
		
		barrier = new CyclicBarrier(CounterAppParallel.howManyNumbers);
		instance = new CounterAppParallel();
		
		instance.countLinearAsync();
		
			
	}
	
	public void countLinearAsync() {
		Thread[] threads = new Thread[howManyNumbers];
		
		for(int i = 0; i < howManyNumbers; i++) {
			threads[i] = new LinearCountingThread(i, barrier);
			threads[i].start();
		}
	}
	
	public static void doneWithLinearCount() {
		System.out.println();
		System.out.println("countLinear: " + linearResult.toString());
		
		instance.countPermutedAsync();
	}

	public void countPermutedAsync() {
		Thread[] threads = new Thread[howManyNumbers];
		
		for(int i = 0; i < howManyNumbers; i++) {
			threads[permutation[i]] = new PermutedCountingThread(i, barrier);
		}
		
		for(int i = 0; i < howManyNumbers; i++) {
			threads[i].start();
		}
	}
	
	public static void doneWithPermutedCount() {
		finishedPermuted = true;
		System.out.println("countPermuted: " + permutedResult.toString());
		
		Float difference = linearResult - permutedResult;
		Float differenceAsPercent = Math.abs(difference*100) / linearResult;
		System.out.println();
		System.out.println("difference: " + difference.toString() + " or " + differenceAsPercent + "%");
	}
	
	public static float countPrefix() {
		
		int count = howManyNumbers;
		int powerOfTwo = nextHighestPowerOfTwo(count);
		
		float[] tempValues = new float[howManyNumbers];
		for(int i = 0; i < howManyNumbers; i++) tempValues[i] = values[i];
		
		while(powerOfTwo > 1) {
			int nextCount = 0;
			for(int i = 0; (i < powerOfTwo && i < count); i+=2) {
				float lhs = tempValues[i];
				float rhs = (i+1 < count ? tempValues[i+1] : 0f);
				tempValues[i/2] = lhs+rhs;
				nextCount ++;
			}
			count = nextCount;
			powerOfTwo /= 2;
		}

		return tempValues[0];
	}
}
