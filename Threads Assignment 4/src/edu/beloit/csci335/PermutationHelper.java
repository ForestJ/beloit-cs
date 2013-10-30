package edu.beloit.csci335;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PermutationHelper {
	public static int[] generateRandomPermutation(int seed, int length) {
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < length; i++) {
			list.add(i);
		}
		Random random = new Random();
		random.setSeed(seed);
		java.util.Collections.shuffle(list, random);
		
		int[] result = new int[length];
		int i = 0;
		for(Integer value : list) {
			result[i] = value;
			i++;
		}
		
		return result;
	}
}
