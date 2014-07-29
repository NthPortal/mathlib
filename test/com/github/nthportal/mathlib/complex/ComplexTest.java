package com.github.nthportal.mathlib.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

		//verify adding negative Complex
		result = new Complex(2, 3).add(new Complex(-3, -4));
		assertEquals(-1, result.getReal(), 0);
		assertEquals(-1, result.getImaginary(), 0);
	}
}
