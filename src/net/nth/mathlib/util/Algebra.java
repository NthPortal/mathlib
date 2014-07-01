package net.nth.mathlib.util;

import java.util.ArrayList;

public class Algebra
{
	public static int gcf(int n1, int n2)
	{
		int larger, smaller, mod;
		
		// Finds which is larger
		if (n1 >= n2)
		{
			larger = n1;
			smaller = n2;
		}
		else
		{
			larger = n2;
			smaller = n1;
		}
		
		// Euclid's Algorithm
		while (smaller != 0)
		{
			mod = (larger % smaller);
			larger = smaller;
			smaller = mod;
		}
		
		if (larger < 0)
		{
			larger *= -1;
		}
		
		return larger;
	}
	
	public static ArrayList<Integer> calcFactors(int num)
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();

		// Stop looping once i reaches the denominator divided by all discovered factors
		for(int i = 2; i <= num; i++)
		{
			while(num % i == 0)
			{
				factors.add(i);
				num /= i;
			}
		}
		
		return factors;
	}
}
