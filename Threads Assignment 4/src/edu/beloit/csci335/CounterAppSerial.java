package edu.beloit.csci335;

import java.util.Random;

public class CounterAppSerial {
	
	public static final float MIN_FLOAT_VALUE = 10f;
	public static final float MAX_FLOAT_VALUE = 100f;
	public static final int GEN_VALUES_SEED = 9238467;
	
	public static int howManyNumbers;
	public static int[] permutation;
	public static float[] values;
	
	public static void main(String[] args) {
		
		int seed = ShellStuff.promptUserForInt("Please enter a random seed: ");
		howManyNumbers = ShellStuff.promptUserForInt("How many numbers do you want to sum? ");
		
		permutation = PermutationHelper.generateRandomPermutation(seed, howManyNumbers);
		values = getRandomFloats(GEN_VALUES_SEED, howManyNumbers);
		
		Float linearResult = countLinear();
		System.out.println();
		System.out.println("countLinear: " + linearResult.toString());
		
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

	
	public static float countLinear() {
		float runningTotal = 0f;
		for(int i = 0; i < howManyNumbers; i++) {
			runningTotal += values[i];
		}
		return runningTotal;
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
	
	
	public static float[] getRandomFloats(int seed, int howManyNumbers) {
		Random random = new Random();
		random.setSeed(seed);
		float randomAmount = MAX_FLOAT_VALUE - MIN_FLOAT_VALUE;
		
		float[] values = new float[howManyNumbers];
		for(int i = 0; i < howManyNumbers; i++) {
			values[i] = MIN_FLOAT_VALUE + (random.nextFloat() * randomAmount);
		}
		
		return values;
	}
	
	public static int nextHighestPowerOfTwo(int n) {
		//http://stackoverflow.com/questions/1322510/given-an-integer-how-do-i-find-the-next-largest-power-of-two-using-bit-twiddlin
		n--;
		n |= n >> 1;   
		n |= n >> 2;   
		n |= n >> 4;
		n |= n >> 8;
		n |= n >> 16;
		n++;
		return n;
	}
	
}
