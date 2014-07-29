package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FractionTest
{

	@Test
	public void testBeanStuff()
	{
		Fraction fraction = new Fraction(5, 2);
		assertEquals(5, fraction.getNumer());
		assertEquals(2, fraction.getDenom());
		assertTrue(fraction.equals(fraction));
	}

	@Test
	public void reducedFraction()
	{
		Fraction fraction = new Fraction(10, 4);
		assertEquals(5, fraction.getNumer());
		assertEquals(2, fraction.getDenom());
	}

	@Test
	public void wholeNumber()
	{
		Fraction fraction = new Fraction(10);
		assertEquals(10, fraction.getNumer());
		assertEquals(1, fraction.getDenom());

		assertTrue(fraction.isInt());
		assertEquals(10, fraction.toInt());

		fraction = new Fraction(10, 3);
		assertEquals(10, fraction.getNumer());
		assertEquals(3, fraction.getDenom());
		assertFalse(fraction.isInt());

		try
		{
			fraction.toInt();
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
		new Fraction(10, 0);
	}

	@Test
	public void negativeDenom()
	{
		Fraction fraction = new Fraction(10, -3);
		assertEquals(-10, fraction.getNumer());
		assertEquals(3, fraction.getDenom());
	}

	@Test
	public void fractionToDouble()
	{
		Fraction fraction = new Fraction(1, 2);
		assertEquals(0.5d, fraction.toDouble(), 0d);
	}

	@Test
	public void reciprocal()
	{
		Fraction fraction = new Fraction(2, 3);
		Fraction reciprocal = fraction.reciprocal();
		assertEquals(3, reciprocal.getNumer());
		assertEquals(2, reciprocal.getDenom());

		try
		{
			new Fraction(0, 3).reciprocal();
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
		Fraction fraction1 = new Fraction(1, 3);
		Fraction fraction2 = new Fraction(1, 3);
		Fraction result = fraction1.add(fraction2);

		// assert the original fractions remained unchanged
		assertTrue(new Fraction(1, 3).equals(fraction1));
		assertTrue(new Fraction(1, 3).equals(fraction2));

		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify improper fraction
		result = new Fraction(2, 3).add(new Fraction(2, 3));
		assertEquals(4, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify adding negative fraction
		result = new Fraction(2, 3).add(new Fraction(-1, 3));
		assertEquals(1, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify adding whole numbers
		result = new Fraction(2, 3).add(1);
		assertEquals(5, result.getNumer());
		assertEquals(3, result.getDenom());
	}

	@Test
	public void subtractFractions()
	{
		Fraction fraction1 = new Fraction(2, 3);
		Fraction fraction2 = new Fraction(1, 3);
		Fraction result = fraction1.subtract(fraction2);

		// assert the original fractions remained unchanged
		assertTrue(new Fraction(2, 3).equals(fraction1));
		assertTrue(new Fraction(1, 3).equals(fraction2));

		assertEquals(1, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify improper fraction
		result = new Fraction(5, 3).subtract(new Fraction(2, 3));
		assertEquals(1, result.getNumer());
		assertEquals(1, result.getDenom());

		// verify subtracting negative fraction
		result = new Fraction(1, 3).subtract(new Fraction(-1, 3));
		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify subtracting whole numbers
		result = new Fraction(5, 3).subtract(1);
		assertEquals(2, result.getNumer());
		assertEquals(3, result.getDenom());

		// verify negative result
		result = new Fraction(1, 3).subtract(new Fraction(2, 3));
		assertEquals(-1, result.getNumer());
		assertEquals(3, result.getDenom());

		// zero as result
		result = new Fraction(1, 3).subtract(new Fraction(1, 3));
		assertEquals(0, result.getNumer());
		assertEquals(1, result.getDenom());
	}

	@Test
	public void multiplyFractions()
	{
		Fraction fraction1 = new Fraction(2, 3);
		Fraction fraction2 = new Fraction(1, 3);
		Fraction result = fraction1.multiply(fraction2);

		// assert the original fractions remained unchanged
		assertTrue(new Fraction(2, 3).equals(fraction1));
		assertTrue(new Fraction(1, 3).equals(fraction2));

		assertEquals(2, result.getNumer());
		assertEquals(9, result.getDenom());
		
		// assert that fraction reduces properly
        result = new Fraction(4, 7).multiply(new Fraction(3, 2));
        assertEquals(6, result.getNumer());
        assertEquals(7, result.getDenom());

        // assert that double negatives cancel
        result = new Fraction(-3, 5).multiply(new Fraction(-6, 4));
        assertEquals(9, result.getNumer());
        assertEquals(10, result.getDenom());
    }

    @Test
    public void divideFractions()
    {
        Fraction fraction1 = new Fraction(2, 3);
        Fraction fraction2 = new Fraction(1, 3);
        Fraction result = fraction1.divide(fraction2);

        // assert the original fractions remained unchanged
        assertTrue(new Fraction(2, 3).equals(fraction1));
        assertTrue(new Fraction(1, 3).equals(fraction2));

        assertEquals(2, result.getNumer());
        assertEquals(1, result.getDenom());

        // assert that fraction reduces properly
        result = new Fraction(4, 7).divide(new Fraction(2, 3));
        assertEquals(6, result.getNumer());
        assertEquals(7, result.getDenom());

        // assert that double negatives cancel
        result = new Fraction(-3, 5).divide(new Fraction(-4, 6));
        assertEquals(9, result.getNumer());
        assertEquals(10, result.getDenom());

        try {
            new Fraction(1, 3).divide(new Fraction(0, 1));
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void powFractions()
    {
        Fraction fraction1 = new Fraction(2, 3);
        Fraction result = fraction1.pow(2);

        // assert the original fraction remained unchanged
        assertTrue(new Fraction(2, 3).equals(fraction1));

        assertEquals(4, result.getNumer());
        assertEquals(9, result.getDenom());

        // assert that ^0 returns 1
        result = new Fraction(3, 4).pow(0);
        assertEquals(1, result.getNumer());
        assertEquals(1, result.getDenom());

        // assert that negative exponent returns reciprocal to positive exponent
        result = new Fraction(3, 4).pow(-3);
        assertEquals(64, result.getNumer());
        assertEquals(27, result.getDenom());
    }
}
