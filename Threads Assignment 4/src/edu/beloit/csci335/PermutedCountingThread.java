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
		float randomValue = CounterAppParallel.values[index];
		CounterAppParallel.permutedResult += randomValue;
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		synchronized(CounterAppParallel.lock) {
			if(!CounterAppParallel.finishedPermuted) {
				CounterAppParallel.doneWithPermutedCount();
			}
		}
	}
}
