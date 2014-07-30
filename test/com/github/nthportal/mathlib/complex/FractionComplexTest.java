package com.github.nthportal.mathlib.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

public class FractionComplexTest
{
    @Test
    public void testBeanStuff()
    {
        FractionComplex complex = new FractionComplex(5, 2);
        assertEquals(new Fraction(5), complex.getReal());
        assertEquals(new Fraction(2), complex.getImaginary());
        assertTrue(complex.equals(complex));
    }

    @Test
    public void conjugate()
    {
        FractionComplex complex = new FractionComplex(1, 4);
        FractionComplex conjugate = complex.conjugate();
        assertEquals(complex.getReal(), conjugate.getReal());
        assertEquals(complex.getImaginary(), (conjugate.getImaginary().multiply(-1)));
    }

    @Test
    public void addComplex() {
        FractionComplex complex1 = new FractionComplex(1, 3);
        FractionComplex complex2 = new FractionComplex(1, 3);
        FractionComplex result = complex1.add(complex2);

        // assert the original FractionComplex numbers remained unchanged
        assertTrue(new FractionComplex(1, 3).equals(complex1));
        assertTrue(new FractionComplex(1, 3).equals(complex2));

        assertEquals(new Fraction(2), result.getReal());
        assertEquals(new Fraction(6), result.getImaginary());

        // verify adding negative FractionComplex
        result = new FractionComplex(2, 3).add(new FractionComplex(-3, -4));
        assertEquals(new Fraction(-1), result.getReal());
        assertEquals(new Fraction(-1), result.getImaginary());

        // assert adding 0 does not change FractionComplex
        result = new FractionComplex(2, 3).add(new FractionComplex(0, 0));
        assertEquals(new Fraction(2), result.getReal());
        assertEquals(new Fraction(3), result.getImaginary());
    }

    @Test
    public void subtractComplex()
    {
        FractionComplex complex1 = new FractionComplex(2, 3);
        FractionComplex complex2 = new FractionComplex(1, 3);
        FractionComplex result = complex1.subtract(complex2);

        // assert the original FractionComplex numbers remained unchanged
        assertTrue(new FractionComplex(2, 3).equals(complex1));
        assertTrue(new FractionComplex(1, 3).equals(complex2));

        assertEquals(new Fraction(1), result.getReal());
        assertEquals(new Fraction(0), result.getImaginary());

        // verify subtracting negative FractionComplex
        result = new FractionComplex(2, 3).subtract(new FractionComplex(-3, -4));
        assertEquals(new Fraction(5), result.getReal());
        assertEquals(new Fraction(7), result.getImaginary());

        // assert subtracting 0 does not change FractionComplex
        result = new FractionComplex(2, 3).subtract(new FractionComplex(0, 0));
        assertEquals(new Fraction(2), result.getReal());
        assertEquals(new Fraction(3), result.getImaginary());
    }

    @Test
    public void multiplyComplex()
    {
        FractionComplex complex1 = new FractionComplex(2, 3);
        FractionComplex complex2 = new FractionComplex(1, 3);
        FractionComplex result = complex1.multiply(complex2);

        // assert the original FractionComplex numbers remained unchanged
        assertTrue(new FractionComplex(2, 3).equals(complex1));
        assertTrue(new FractionComplex(1, 3).equals(complex2));

        assertEquals(new Fraction(-7), result.getReal());
        assertEquals(new Fraction(9), result.getImaginary());

        // assert multiplying by 0 yields 0
        result = new FractionComplex(2, 3).multiply(new FractionComplex(0, 0));
        assertEquals(new Fraction(0), result.getReal());
        assertEquals(new Fraction(0), result.getImaginary());

        // assert multiplying by 1 yields original
        result = new FractionComplex(2, 3).multiply(new FractionComplex(1, 0));
        assertEquals(new Fraction(2), result.getReal());
        assertEquals(new Fraction(3), result.getImaginary());

        // assert multiplying by negatives works properly
        result = new FractionComplex(3, -4).multiply(new FractionComplex(-2, 2));
        assertEquals(new Fraction(2), result.getReal());
        assertEquals(new Fraction(14), result.getImaginary());
    }

    @Test
    public void divideComplex()
    {
        FractionComplex complex1 = new FractionComplex(2, 3);
        FractionComplex complex2 = new FractionComplex(1, 3);
        FractionComplex result = complex1.divide(complex2);

        // assert the original FractionComplex numbers remained unchanged
        assertTrue(new FractionComplex(2, 3).equals(complex1));
        assertTrue(new FractionComplex(1, 3).equals(complex2));

        assertEquals(new Fraction(11, 10), result.getReal());
        assertEquals(new Fraction(-3, 10), result.getImaginary());

        // assert that dividing by negatives works
        result = new FractionComplex(2, 3).divide(new FractionComplex(-1, -3));
        assertEquals(new Fraction(-11, 10), result.getReal());
        assertEquals(new Fraction(3, 10), result.getImaginary());

        result = new FractionComplex(2, 3).divide(new FractionComplex(-1, 3));
        assertEquals(new Fraction( 7, 10), result.getReal());
        assertEquals(new Fraction(-9, 10), result.getImaginary());

        // verify that dividing by 0 throws an error
        try {
            new FractionComplex(1, 3).divide(new FractionComplex(0, 0));
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
        FractionComplex complex1 = new FractionComplex(2, 3);
        FractionComplex result = complex1.pow(3);

        // assert the original FractionComplex numbers remained unchanged
        assertTrue(new FractionComplex(2, 3).equals(complex1));

        assertEquals(new Fraction(-46), result.getReal());
        assertEquals(new Fraction(9), result.getImaginary());

        // verify simple powers of i
        result = new FractionComplex(0, 1).pow(4);
        assertEquals(new Fraction(1), result.getReal());
        assertEquals(new Fraction(0), result.getImaginary());

        result = new FractionComplex(0, 1).pow(5);
        assertEquals(new Fraction(0), result.getReal());
        assertEquals(new Fraction(1), result.getImaginary());

        // verify that 0 power yields 1 + 0i
        result = new FractionComplex(6, 7).pow(0);
        assertEquals(new Fraction(1), result.getReal());
        assertEquals(new Fraction(0), result.getImaginary());

        // verify error for negative exponents
        try {
            new FractionComplex(1, 3).pow(-3);
            fail("expected exception: IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // success
        }
    }
}
