package net.nth.mathlib.util;

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
}
