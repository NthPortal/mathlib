package com.github.nthportal.mathlib.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

public class ComplexTest
{
	@Test
	public void testBeanStuff()
	{
		Complex complex = new Complex(5, 2);
		assertEquals(5, complex.getReal(), 0);
		assertEquals(2, complex.getImaginary(), 0);
		assertTrue(complex.equals(complex));
	}
	
	@Test
	public void conjugate()
	{
		Complex complex = new Complex(1, 4);
		Complex conjugate = complex.conjugate();
		assertEquals(complex.getReal(), conjugate.getReal(), 0);
		assertEquals(complex.getImaginary(), (conjugate.getImaginary() * -1), 0);
	}
	
	@Test
	public void addComplex() {
		Complex complex1 = new Complex(1, 3);
		Complex complex2 = new Complex(1, 3);
		Complex result = complex1.add(complex2);

		// assert the original Complex numbers remained unchanged
		assertTrue(new Complex(1, 3).equals(complex1));
		assertTrue(new Complex(1, 3).equals(complex2));

		assertEquals(2, result.getReal(), 0);
		assertEquals(6, result.getImaginary(), 0);

		// verify adding negative Complex
		result = new Complex(2, 3).add(new Complex(-3, -4));
		assertEquals(-1, result.getReal(), 0);
		assertEquals(-1, result.getImaginary(), 0);

        // assert adding 0 does not change Complex
        result = new Complex(2, 3).add(new Complex(0, 0));
        assertEquals(2, result.getReal(), 0);
        assertEquals(3, result.getImaginary(), 0);
	}

    @Test
    public void subtractComplex()
    {
        Complex complex1 = new Complex(2, 3);
        Complex complex2 = new Complex(1, 3);
        Complex result = complex1.subtract(complex2);

        // assert the original Complex numbers remained unchanged
        assertTrue(new Complex(2, 3).equals(complex1));
        assertTrue(new Complex(1, 3).equals(complex2));

        assertEquals(1, result.getReal(), 0);
        assertEquals(0, result.getImaginary(), 0);

        // verify subtracting negative Complex
        result = new Complex(2, 3).subtract(new Complex(-3, -4));
        assertEquals(5, result.getReal(), 0);
        assertEquals(7, result.getImaginary(), 0);

        // assert subtracting 0 does not change Complex
        result = new Complex(2, 3).subtract(new Complex(0, 0));
        assertEquals(2, result.getReal(), 0);
        assertEquals(3, result.getImaginary(), 0);
    }

    @Test
    public void multiplyComplex()
    {
        Complex complex1 = new Complex(2, 3);
        Complex complex2 = new Complex(1, 3);
        Complex result = complex1.multiply(complex2);

        // assert the original Complex numbers remained unchanged
        assertTrue(new Complex(2, 3).equals(complex1));
        assertTrue(new Complex(1, 3).equals(complex2));

        assertEquals(-7, result.getReal(), 0);
        assertEquals(9, result.getImaginary(), 0);

        // assert multiplying by 0 yields 0
        result = new Complex(2, 3).multiply(new Complex(0, 0));
        assertEquals(0, result.getReal(), 0);
        assertEquals(0, result.getImaginary(), 0);

        // assert multiplying by 1 yields original
        result = new Complex(2, 3).multiply(new Complex(1, 0));
        assertEquals(2, result.getReal(), 0);
        assertEquals(3, result.getImaginary(), 0);

        // assert multiplying by negatives works properly
        result = new Complex(3, -4).multiply(new Complex(-2, 2));
        assertEquals(2, result.getReal(), 0);
        assertEquals(14, result.getImaginary(), 0);
    }

    @Test
    public void divideComplex()
    {
        Complex complex1 = new Complex(2, 3);
        Complex complex2 = new Complex(1, 3);
        Complex result = complex1.divide(complex2);

        // assert the original Complex numbers remained unchanged
        assertTrue(new Complex(2, 3).equals(complex1));
        assertTrue(new Complex(1, 3).equals(complex2));

        assertEquals(1.1, result.getReal(), 0);
        assertEquals(( (double) -3 / 10), result.getImaginary(), 0);

        // assert that dividing by negatives works
        result = new Complex(2, 3).divide(new Complex(-1, -3));
        assertEquals(( -1.1), result.getReal(), 0);
        assertEquals(( (double) 3 / 10), result.getImaginary(), 0);

        result = new Complex(2, 3).divide(new Complex(-1, 3));
        assertEquals(( (double) 7 / 10), result.getReal(), 0);
        assertEquals(( (double) -9 / 10), result.getImaginary(), 0);

        // verify that dividing by 0 throws an error
        try {
            new Complex(1, 3).divide(new Complex(0, 0));
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void powComplex()
    {
        Complex complex1 = new Complex(2, 3);
        Complex result = complex1.pow(3);

        // assert the original Complex numbers remained unchanged
        assertTrue(new Complex(2, 3).equals(complex1));

        assertEquals(-46, result.getReal(), 0);
        assertEquals(9, result.getImaginary(), 0);

        // verify simple powers of i
        result = new Complex(0, 1).pow(4);
        assertEquals(1, result.getReal(), 0);
        assertEquals(0, result.getImaginary(), 0);

        result = new Complex(0, 1).pow(5);
        assertEquals(0, result.getReal(), 0);
        assertEquals(1, result.getImaginary(), 0);

        // verify that 0 power yields 1 + 0i
        result = new Complex(6, 7).pow(0);
        assertEquals(1, result.getReal(), 0);
        assertEquals(0, result.getImaginary(), 0);

        // verify error for negative exponents
        try {
            new Complex(1, 3).pow(-3);
            fail("expected exception: IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // success
        }
    }
}
