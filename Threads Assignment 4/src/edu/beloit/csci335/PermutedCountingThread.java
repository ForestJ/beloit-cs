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
		
		float randomValue = CounterApp.values[CounterApp.permutation[index]];
		
		try {
			barrier.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		
		synchronized(CounterApp.lock) {
			while(CounterApp.nextIndex != index) {
				try {
					CounterApp.lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		CounterApp.permutedResult += randomValue;
		
		CounterApp.nextIndex++;
		if(CounterApp.nextIndex >= CounterApp.howManyNumbers) {
			CounterApp.doneWithPermutedCount();
		}
		
		synchronized(CounterApp.lock) {
			CounterApp.lock.notifyAll();
		}
	}
}
