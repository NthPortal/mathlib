package com.github.nthportal.mathlib.polynomial;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FracPolyUnitTest
{
    @Test
    public void testBeanStuff()
    {
        FracPolyUnit p = new FracPolyUnit(5, 2);
        assertEquals(new Fraction(5), p.getCoefficient());
        assertEquals(2, p.getExponent());
        assertTrue(p.equals(p));
    }

    @Test
    public void addFracPolyUnit()
    {
        FracPolyUnit p1 = new FracPolyUnit(2, 3);
        FracPolyUnit p2 = new FracPolyUnit(3, 3);
        FracPolyUnit result = p1.add(p2);

        // assert the original FracPolyUnits remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p1));
        assertTrue(new FracPolyUnit(3, 3).equals(p2));

        assertEquals(new Fraction(5), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify adding a negative FracPolyUnit
        result = new FracPolyUnit(1, 2).add(new FracPolyUnit(-2, 2));
        assertEquals(new Fraction(-1), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that adding 0 doesn't change the FracPolyUnit
        // assert that constructing a FracPolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new FracPolyUnit(2, 0).add(new FracPolyUnit(0, 1));
        assertEquals(new Fraction(2), result.getCoefficient());
        assertEquals(0, result.getExponent());

        // verify that adding FracPolyUnits with different exponents throws an error
        try {
            new FracPolyUnit(1, 3).add(new FracPolyUnit(2, 2));
            fail("expected exception: DifferentOrderMonomialException");
        }
        catch (DifferentOrderMonomialException e)
        {
            // success
        }
    }

    @Test
    public void subtractFracPolyUnit()
    {
        FracPolyUnit p1 = new FracPolyUnit(3, 3);
        FracPolyUnit p2 = new FracPolyUnit(2, 3);
        FracPolyUnit result = p1.subtract(p2);

        // assert the original FracPolyUnits remained unchanged
        assertTrue(new FracPolyUnit(3, 3).equals(p1));
        assertTrue(new FracPolyUnit(2, 3).equals(p2));

        assertEquals(new Fraction(1), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify subtracting a negative FracPolyUnit
        result = new FracPolyUnit(1, 2).subtract(new FracPolyUnit(-2, 2));
        assertEquals(new Fraction(3), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that subtracting 0 doesn't change the FracPolyUnit
        // assert that constructing a FracPolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new FracPolyUnit(2, 0).subtract(new FracPolyUnit(0, 1));
        assertEquals(new Fraction(2), result.getCoefficient());
        assertEquals(0, result.getExponent());

        // verify that subtracting FracPolyUnits with different exponents throws an error
        try {
            new FracPolyUnit(1, 3).subtract(new FracPolyUnit(2, 2));
            fail("expected exception: DifferentOrderMonomialException");
        }
        catch (DifferentOrderMonomialException e)
        {
            // success
        }
    }

    @Test
    public void multiplyFracPolyUnit()
    {
        FracPolyUnit p1 = new FracPolyUnit(2, 3);
        FracPolyUnit p2 = new FracPolyUnit(3, 3);
        FracPolyUnit result = p1.multiply(p2);

        // assert the original FracPolyUnits remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p1));
        assertTrue(new FracPolyUnit(3, 3).equals(p2));

        assertEquals(new Fraction(6), result.getCoefficient());
        assertEquals(6, result.getExponent());

        // verify multiplying a negative FracPolyUnit
        result = new FracPolyUnit(1, 2).multiply(new FracPolyUnit(-2, 2));
        assertEquals(new Fraction(-2), result.getCoefficient());
        assertEquals(4, result.getExponent());

        // assert that multiplying by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(3, 4).multiply(new FracPolyUnit(1, 0));
        assertEquals(new Fraction(3), result.getCoefficient());
        assertEquals(4, result.getExponent());

        // assert that multiplying by 0 doesn't yields 0
        // assert that constructing a FracPolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new FracPolyUnit(2, 0).multiply(new FracPolyUnit(0, 1));
        assertEquals(new Fraction(0), result.getCoefficient());
        assertEquals(0, result.getExponent());
    }

    @Test
    public void multiplyInt()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.multiply(4);

        // assert the original FracPolyUnit remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(8), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify multiplying a negative double
        result = new FracPolyUnit(1, 2).multiply(-3);
        assertEquals(new Fraction(-3), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that multiplying by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(3, 4).multiply(1);
        assertEquals(new Fraction(3), result.getCoefficient());
        assertEquals(4, result.getExponent());

        // assert that multiplying by 0 yields 0
        // assert that constructing a FracPolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new FracPolyUnit(2, 0).multiply(0);
        assertEquals(new Fraction(0), result.getCoefficient());
        assertEquals(0, result.getExponent());
    }

    @Test
    public void multiplyFraction()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.multiply(new Fraction(4));

        // assert the original FracPolyUnit remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(8), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify multiplying a negative double
        result = new FracPolyUnit(1, 2).multiply(new Fraction(-3));
        assertEquals(new Fraction(-3), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that multiplying by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(3, 4).multiply(new Fraction(1));
        assertEquals(new Fraction(3), result.getCoefficient());
        assertEquals(4, result.getExponent());

        // assert that multiplying by 0 yields 0
        // assert that constructing a FracPolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new FracPolyUnit(2, 0).multiply(new Fraction(0));
        assertEquals(new Fraction(0), result.getCoefficient());
        assertEquals(0, result.getExponent());
    }

    @Test
    public void divideFracPolyUnit()
    {
        FracPolyUnit p1 = new FracPolyUnit(2, 3);
        FracPolyUnit p2 = new FracPolyUnit(3, 3);
        FracPolyUnit result = p1.divide(p2);

        // assert the original FracPolyUnits remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p1));
        assertTrue(new FracPolyUnit(3, 3).equals(p2));

        assertEquals(new Fraction(2, 3), result.getCoefficient());
        assertEquals(0, result.getExponent());

        // verify dividing a negative FracPolyUnit
        result = new FracPolyUnit(1, 2).divide(new FracPolyUnit(-2, 2));
        assertEquals(new Fraction(-1, 2), result.getCoefficient());
        assertEquals(0, result.getExponent());

        // assert that dividing by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(2, 3).divide(new FracPolyUnit(1, 0));
        assertEquals(new Fraction(2), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify that dividing by 0 throws an error
        try {
            new FracPolyUnit(1, 3).divide(new FracPolyUnit(0, 0));
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void divideInt()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.divide(4);

        // assert the original FracPolyUnit remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(1, 2), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify dividing a negative double
        result = new FracPolyUnit(1, 2).divide(-2);
        assertEquals(new Fraction(-1, 2), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that dividing by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(2, 3).divide(1);
        assertEquals(new Fraction(2), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify that dividing by 0 throws an error
        try {
            new FracPolyUnit(1, 3).divide(0);
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void divideFraction()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.divide(new Fraction(4));

        // assert the original FracPolyUnit remained unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(1, 2), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify dividing a negative double
        result = new FracPolyUnit(1, 2).divide(new Fraction(-2));
        assertEquals(new Fraction(-1, 2), result.getCoefficient());
        assertEquals(2, result.getExponent());

        // assert that dividing by 1 doesn't change the FracPolyUnit
        result = new FracPolyUnit(2, 3).divide(new Fraction(1));
        assertEquals(new Fraction(2), result.getCoefficient());
        assertEquals(3, result.getExponent());

        // verify that dividing by 0 throws an error
        try {
            new FracPolyUnit(1, 3).divide(new Fraction(0));
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void evalInt()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        Fraction result = p.eval(3);

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(54), result);

        // verify evaluating at a negative number
        result = new FracPolyUnit(3, 2).eval(-2);
        assertEquals(new Fraction(12), result);

        result = new FracPolyUnit(3, 3).eval(-2);
        assertEquals(new Fraction(-24), result);

        // assert that evaluating at 1 returns the coefficient
        result = new FracPolyUnit(3, 2).eval(1);
        assertEquals(new Fraction(3), result);

        // assert that evaluating at 0 returns 0
        result = new FracPolyUnit(2, 2).eval(0);
        assertEquals(new Fraction(0), result);
    }

    @Test
    public void evalFraction()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        Fraction result = p.eval(new Fraction(3));

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new Fraction(54), result);

        // verify evaluating at a negative number
        result = new FracPolyUnit(3, 2).eval(new Fraction(-2));
        assertEquals(new Fraction(12), result);

        result = new FracPolyUnit(3, 3).eval(new Fraction(-2));
        assertEquals(new Fraction(-24), result);

        // assert that evaluating at 1 returns the coefficient
        result = new FracPolyUnit(3, 2).eval(new Fraction(1));
        assertEquals(new Fraction(3), result);

        // assert that evaluating at 0 returns 0
        result = new FracPolyUnit(2, 2).eval(new Fraction(0));
        assertEquals(new Fraction(0), result);
    }

    @Test
    public void deriveFracPolyUnit()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.derivative();

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new FracPolyUnit(6, 2), result);

        // verify exponent of value 1
        result = new FracPolyUnit(2, 1).derivative();
        assertEquals(new FracPolyUnit(2, 0), result);

        // verify exponent of value 0 yields 0
        result = new FracPolyUnit(2, 0).derivative();
        assertEquals(new FracPolyUnit(0, 0), result);

        // verify negative exponent
        result = new FracPolyUnit(2, -1).derivative();
        assertEquals(new FracPolyUnit(-2, -2), result);

        result = new FracPolyUnit(-3, -2).derivative();
        assertEquals(new FracPolyUnit(6, -3), result);
    }

    @Test
    public void antiDeriveFracPolyUnit()
    {
        FracPolyUnit p = new FracPolyUnit(2, 3);
        FracPolyUnit result = p.antiDerivative();

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(2, 3).equals(p));

        assertEquals(new FracPolyUnit(new Fraction(1, 2), 4), result);

        // verify exponent of value 1
        result = new FracPolyUnit(2, 1).antiDerivative();
        assertEquals(new FracPolyUnit(1, 2), result);

        // verify exponent of value 0
        result = new FracPolyUnit(2, 0).antiDerivative();
        assertEquals(new FracPolyUnit(2, 1), result);

        // verify negative exponent
        result = new FracPolyUnit(-3, -2).antiDerivative();
        assertEquals(new FracPolyUnit(3, -1), result);
    }

    @Test
    public void integrateInt()
    {
        FracPolyUnit p = new FracPolyUnit(6, 2);
        Fraction result = p.integral(0, 3);

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(6, 2).equals(p));

        assertEquals(new Fraction(54), result);

        // verify integrating with reversed bounds yields the negative of the result
        result = new FracPolyUnit(6, 2).integral(3, 0);
        assertEquals(new Fraction(-54), result);

        // assert that integrating over a range of size 0 returns 0
        result = new FracPolyUnit(2, 2).integral(5, 5);
        assertEquals(new Fraction(0), result);

        // verify that integrating with non-zero bounds works
        result = new FracPolyUnit(1, 2).integral(3, 6);
        assertEquals(new Fraction(63), result);
    }

    @Test
    public void integrateFraction()
    {
        FracPolyUnit p = new FracPolyUnit(6, 2);
        Fraction result = p.integral(new Fraction(0), new Fraction(3));

        // assert the original FracPolyUnit remains unchanged
        assertTrue(new FracPolyUnit(6, 2).equals(p));

        assertEquals(new Fraction(54), result);

        // verify integrating with reversed bounds yields the negative of the result
        result = new FracPolyUnit(6, 2).integral(new Fraction(3), new Fraction(0));
        assertEquals(new Fraction(-54), result);

        // assert that integrating over a range of size 0 returns 0
        result = new FracPolyUnit(2, 2).integral(new Fraction(5), new Fraction(5));
        assertEquals(new Fraction(0), result);

        // verify that integrating with non-zero bounds works
        result = new FracPolyUnit(1, 2).integral(new Fraction(3), new Fraction(6));
        assertEquals(new Fraction(63), result);
    }
}
