package edu.beloit.csci335;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterAppParallel extends CounterAppSerial {
	
	class LinearCountingThread extends Thread {
		private int index;
		private CyclicBarrier barrier;
		
		public LinearCountingThread (int index, CyclicBarrier barrier) {
			this.index = index;
			this.barrier = barrier;
		}
		
		public void run() {
			/*
			Random random = new Random();
			random.setSeed(index);
			float randomAmount = MAX_FLOAT_VALUE - MIN_FLOAT_VALUE;
			float randomValue = MIN_FLOAT_VALUE + (random.nextFloat() * randomAmount);
			*/
			
			float randomValue = CounterAppParallel.values[index];
			
			synchronized(CounterAppParallel.lock) {
				while(CounterAppParallel.nextIndex != index) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			CounterAppParallel.linearResult += CounterAppParallel.values[index];
			
			CounterAppParallel.nextIndex++;
			if(CounterAppParallel.nextIndex >= CounterAppParallel.howManyNumbers) {
				CounterAppParallel.doneWithLinearCount();
			}
			
			synchronized(CounterAppParallel.lock) {
				CounterAppParallel.lock.notifyAll();
			}
		}

	}
	
	public static Object lock = new Object();
	public static CyclicBarrier linearBarrier;
	public static int nextIndex = 0;
	public static Float linearResult;
	public static Float permutedResult;
	public static Float prefixResult;
	
	public static void doCounting () {
		linearBarrier = new CyclicBarrier(CounterAppParallel.howManyNumbers);
		CounterAppParallel instance = new CounterAppParallel();
		
		instance.countLinearAsync();
		
		Float permutedResult = countPermuted();
		System.out.println("countPermuted: " + permutedResult.toString());
		
		Float difference = linearResult - permutedResult;
		Float differenceAsPercent = Math.abs(difference*100) / linearResult;
		System.out.println();
		System.out.println("difference: " + difference.toString() + " or " + differenceAsPercent + "%");
		
		Float prefixResult = countPrefix();
		System.out.println();
		System.out.println("countPrefix: " + prefixResult.toString());
		
		difference = linearResult - prefixResult;
		differenceAsPercent = Math.abs(difference*100) / linearResult;
		System.out.println();
		System.out.println("difference: " + difference.toString() + " or " + differenceAsPercent + "%");	
	}
	
	public void countLinearAsync() {
		Thread[] threads = new Thread[howManyNumbers];
		
		for(int i = 0; i < howManyNumbers; i++) {
			threads[i] = new LinearCountingThread(i, linearBarrier);
		}
	}
	
	public static void doneWithLinearCount() {
		System.out.println();
		System.out.println("countLinear: " + linearResult.toString());
	}

	public static float countPermuted() {
		float runningTotal = 0f;
		for(int i = 0; i < howManyNumbers; i++) {
			runningTotal += values[permutation[i]];
		}
		return runningTotal;
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
