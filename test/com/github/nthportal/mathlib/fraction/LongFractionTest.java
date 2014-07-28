package com.github.nthportal.mathlib.fraction;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongFractionTest
{
	@Test
	public void testBeanStuff()
	{
		LongFraction LongFraction = new LongFraction(5, 2);
		assertEquals(5, LongFraction.getNumer());
		assertEquals(2, LongFraction.getDenom());
	}

	@Test
	public void reducedFraction()
	{
		LongFraction LongFraction = new LongFraction(10, 4);
		assertEquals(5, LongFraction.getNumer());
		assertEquals(2, LongFraction.getDenom());
	}

	@Test
	public void wholeNumber()
	{
		LongFraction LongFraction = new LongFraction(10);
		assertEquals(10, LongFraction.getNumer());
		assertEquals(1, LongFraction.getDenom());

		assertTrue(LongFraction.isInt());
		assertEquals(10L, LongFraction.toLong());

		LongFraction = new LongFraction(10, 3);
		assertEquals(10, LongFraction.getNumer());
		assertEquals(3, LongFraction.getDenom());
		assertFalse(LongFraction.isInt());

		try
		{
			LongFraction.toLong();
			fail("expected exception: NonIntFractionException");
		}
		catch (NonIntFractionException e)
		{
			// success
		}
	}

	@Test(expected = ZeroDenomException.class)
	public void badDenom()
	{
		new LongFraction(10, 0);
	}

	@Test
	public void negativeDenom()
	{
		LongFraction LongFraction = new LongFraction(10, -3);
		assertEquals(-10, LongFraction.getNumer());
		assertEquals(3, LongFraction.getDenom());
	}

	@Test
	public void fractionToDouble()
	{
		LongFraction LongFraction = new LongFraction(1, 2);
		assertEquals(0.5d, LongFraction.toDouble(), 0d);
	}

	@Test
	public void reciprocal()
	{
		LongFraction LongFraction = new LongFraction(2, 3);
		LongFraction reciprocal = LongFraction.reciprocal();
		assertEquals(3, reciprocal.getNumer());
		assertEquals(2, reciprocal.getDenom());

		try
		{
			new LongFraction(0, 3).reciprocal();
			fail("expected exception: ZeroDenomException");
		}
		catch (ZeroDenomException e)
		{
			// success
		}
	}

	@Test
	public void addFractions()
	{
		LongFraction fraction1 = new LongFraction(1, 3);
		LongFraction fraction2 = new LongFraction(1, 3);
		LongFraction result = fraction1.add(fraction2);

		// assert the original fractions remained unchanged
		assertTrue(new LongFraction(1, 3).equals(fraction1));
		assertTrue(new LongFraction(1, 3).equals(fraction2));

		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify improper LongFraction
		result = new LongFraction(2, 3).add(new LongFraction(2, 3));
		assertEquals(4, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify adding negative LongFraction
		result = new LongFraction(2, 3).add(new LongFraction(-1, 3));
		assertEquals(1, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify adding whole numbers
		result = new LongFraction(2, 3).add(1);
		assertEquals(5, result.getNumer());
		assertEquals(3, result.getDenom());
	}

	@Test
	public void subtractFractions()
	{
		LongFraction fraction1 = new LongFraction(2, 3);
		LongFraction fraction2 = new LongFraction(1, 3);
		LongFraction result = fraction1.subtract(fraction2);

		// assert the original fractions remained unchanged
		assertTrue(new LongFraction(2, 3).equals(fraction1));
		assertTrue(new LongFraction(1, 3).equals(fraction2));

		assertEquals(1, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify improper LongFraction
		result = new LongFraction(5, 3).subtract(new LongFraction(2, 3));
		assertEquals(1, result.getNumer());
		assertEquals(1, result.getDenom());

		// verify subtracting negative LongFraction
		result = new LongFraction(1, 3).subtract(new LongFraction(-1, 3));
		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify subtracting whole numbers
		result = new LongFraction(5, 3).subtract(1);
		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify negative result
		result = new LongFraction(1, 3).subtract(new LongFraction(2, 3));
		assertEquals(-1, result.getNumer());
		assertEquals(3, result.getDenom());

		// zero as result
		result = new LongFraction(1, 3).subtract(new LongFraction(1, 3));
		assertEquals(0, result.getNumer());
		assertEquals(1, result.getDenom());
	}
}
