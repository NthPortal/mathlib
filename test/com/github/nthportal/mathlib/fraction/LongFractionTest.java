package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongFractionTest
{
    @Test
    public void testBeanStuff()
    {
        LongFraction fraction = new LongFraction(5, 2);
        assertEquals(5, fraction.getNumer());
        assertEquals(2, fraction.getDenom());
        assertTrue(fraction.equals(fraction));
    }

    @Test
    public void reducedLongFraction()
    {
        LongFraction fraction = new LongFraction(10, 4);
        assertEquals(5, fraction.getNumer());
        assertEquals(2, fraction.getDenom());
    }

    @Test
    public void wholeNumber()
    {
        LongFraction fraction = new LongFraction(10);
        assertEquals(10, fraction.getNumer());
        assertEquals(1, fraction.getDenom());

        assertTrue(fraction.isInt());
        assertEquals(10, fraction.toLong());

        fraction = new LongFraction(10, 3);
        assertEquals(10, fraction.getNumer());
        assertEquals(3, fraction.getDenom());
        assertFalse(fraction.isInt());

        try
        {
            fraction.toLong();
            fail("expected exception: NonIntLongFractionException");
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
        LongFraction fraction = new LongFraction(10, -3);
        assertEquals(-10, fraction.getNumer());
        assertEquals(3, fraction.getDenom());
    }

    @Test
    public void fractionToDouble()
    {
        LongFraction fraction = new LongFraction(1, 2);
        assertEquals(0.5d, fraction.toDouble(), 0d);
    }

    @Test
    public void reciprocal()
    {
        LongFraction fraction = new LongFraction(2, 3);
        LongFraction reciprocal = fraction.reciprocal();
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
    public void addLongFractions()
    {
        LongFraction fraction1 = new LongFraction(1, 3);
        LongFraction fraction2 = new LongFraction(1, 3);
        LongFraction result = fraction1.add(fraction2);

        // assert the original fractions remained unchanged
        assertTrue(new LongFraction(1, 3).equals(fraction1));
        assertTrue(new LongFraction(1, 3).equals(fraction2));

        assertEquals(2, result.getNumer());
        assertEquals(3, result.getDenom());

        // verify improper fraction
        result = new LongFraction(2, 3).add(new LongFraction(2, 3));
        assertEquals(4, result.getNumer());
        assertEquals(3, result.getDenom());

        // verify adding negative fraction
        result = new LongFraction(2, 3).add(new LongFraction(-1, 3));
        assertEquals(1, result.getNumer());
        assertEquals(3, result.getDenom());

        // verify adding whole numbers
        result = new LongFraction(2, 3).add(1);
        assertEquals(5, result.getNumer());
        assertEquals(3, result.getDenom());
    }

    @Test
    public void subtractLongFractions()
    {
        LongFraction fraction1 = new LongFraction(2, 3);
        LongFraction fraction2 = new LongFraction(1, 3);
        LongFraction result = fraction1.subtract(fraction2);

        // assert the original fractions remained unchanged
        assertTrue(new LongFraction(2, 3).equals(fraction1));
        assertTrue(new LongFraction(1, 3).equals(fraction2));

        assertEquals(1, result.getNumer());
        assertEquals(3, result.getDenom());

        // verify improper fraction
        result = new LongFraction(5, 3).subtract(new LongFraction(2, 3));
        assertEquals(1, result.getNumer());
        assertEquals(1, result.getDenom());

        // verify subtracting negative fraction
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

    @Test
    public void multiplyLongFractions()
    {
        LongFraction fraction1 = new LongFraction(2, 3);
        LongFraction fraction2 = new LongFraction(1, 3);
        LongFraction result = fraction1.multiply(fraction2);

        // assert the original fractions remained unchanged
        assertTrue(new LongFraction(2, 3).equals(fraction1));
        assertTrue(new LongFraction(1, 3).equals(fraction2));

        assertEquals(2, result.getNumer());
        assertEquals(9, result.getDenom());

        // assert that fraction reduces properly
        result = new LongFraction(4, 7).multiply(new LongFraction(3, 2));
        assertEquals(6, result.getNumer());
        assertEquals(7, result.getDenom());

        // assert that double negatives cancel
        result = new LongFraction(-3, 5).multiply(new LongFraction(-6, 4));
        assertEquals(9, result.getNumer());
        assertEquals(10, result.getDenom());
    }

    @Test
    public void divideLongFractions()
    {
        LongFraction fraction1 = new LongFraction(2, 3);
        LongFraction fraction2 = new LongFraction(1, 3);
        LongFraction result = fraction1.divide(fraction2);

        // assert the original fractions remained unchanged
        assertTrue(new LongFraction(2, 3).equals(fraction1));
        assertTrue(new LongFraction(1, 3).equals(fraction2));

        assertEquals(2, result.getNumer());
        assertEquals(1, result.getDenom());

        // assert that fraction reduces properly
        result = new LongFraction(4, 7).divide(new LongFraction(2, 3));
        assertEquals(6, result.getNumer());
        assertEquals(7, result.getDenom());

        // assert that double negatives cancel
        result = new LongFraction(-3, 5).divide(new LongFraction(-4, 6));
        assertEquals(9, result.getNumer());
        assertEquals(10, result.getDenom());

        try {
            new LongFraction(1, 3).divide(new LongFraction(0, 1));
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
        LongFraction fraction1 = new LongFraction(2, 3);
        LongFraction result = fraction1.pow(2);

        // assert the original fraction remained unchanged
        assertTrue(new LongFraction(2, 3).equals(fraction1));

        assertEquals(4, result.getNumer());
        assertEquals(9, result.getDenom());

        // assert that ^0 returns 1
        result = new LongFraction(3, 4).pow(0);
        assertEquals(1, result.getNumer());
        assertEquals(1, result.getDenom());

        // assert that negative exponent returns reciprocal to positive exponent
        result = new LongFraction(3, 4).pow(-3);
        assertEquals(64, result.getNumer());
        assertEquals(27, result.getDenom());
    }
}
