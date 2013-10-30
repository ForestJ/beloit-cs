package edu.beloit.csci335;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LinearCountingThread extends Thread {
	private int index;
	private CyclicBarrier barrier;
	
	public LinearCountingThread (int index, CyclicBarrier barrier) {
		this.index = index;
		this.barrier = barrier;
	}
	
	public void run() {
		
		float randomValue = CounterAppParallel.values[index];
		
		try {
			barrier.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		
		synchronized(CounterAppParallel.lock) {
			while(CounterAppParallel.nextIndex != index) {
				try {
					CounterAppParallel.lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		CounterAppParallel.linearResult += randomValue;
		
		CounterAppParallel.nextIndex++;
		if(CounterAppParallel.nextIndex >= CounterAppParallel.howManyNumbers) {
			CounterAppParallel.doneWithLinearCount();
		}
		
		synchronized(CounterAppParallel.lock) {
			CounterAppParallel.lock.notifyAll();
		}
	}
}