package edu.beloit.csci335;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class PermutedCountingThread extends Thread {
	private int index;
	private CyclicBarrier barrier;
	
	public PermutedCountingThread (int index, CyclicBarrier barrier) {
		this.index = index;
		this.barrier = barrier;
	}
	
	public void run() {
		float randomValue = CounterApp.values[index];
		CounterApp.permutedResult += randomValue;
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		synchronized(CounterApp.lock) {
			if(!CounterApp.finishedPermuted) {
				CounterApp.doneWithPermutedCount();
			}
		}
	}
}
