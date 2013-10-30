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
		
		float randomValue = CounterApp.values[index];
		
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
		
		CounterApp.linearResult += randomValue;
		
		CounterApp.nextIndex++;
		if(CounterApp.nextIndex >= CounterApp.howManyNumbers) {
			CounterApp.doneWithLinearCount();
		}
		
		synchronized(CounterApp.lock) {
			CounterApp.lock.notifyAll();
		}
	}
}