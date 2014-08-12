package com.github.nthportal.mathlib.polynomial;

import com.github.nthportal.mathlib.util.ZeroDivisionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PolyUnitTest
{
    @Test
    public void testBeanStuff()
    {
        PolyUnit p = new PolyUnit(5, 2);
        assertEquals(5, p.getCoefficient(), 0);
        assertEquals(2, p.getExponent(), 0);
        assertTrue(p.equals(p));
    }

    @Test
    public void addPolyUnit()
    {
        PolyUnit p1 = new PolyUnit(2, 3);
        PolyUnit p2 = new PolyUnit(3, 3);
        PolyUnit result = p1.add(p2);

        // assert the original PolyUnits remained unchanged
        assertTrue(new PolyUnit(2, 3).equals(p1));
        assertTrue(new PolyUnit(3, 3).equals(p2));

        assertEquals(5, result.getCoefficient(), 0);
        assertEquals(3, result.getExponent(), 0);

        // verify adding a negative PolyUnit
        result = new PolyUnit(1, 2).add(new PolyUnit(-2, 2));
        assertEquals(-1, result.getCoefficient(), 0);
        assertEquals(2, result.getExponent(), 0);

        // assert that adding 0 doesn't change the PolyUnit
        // assert that constructing a PolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new PolyUnit(2, 0).add(new PolyUnit(0, 1));
        assertEquals(2, result.getCoefficient(), 0);
        assertEquals(0, result.getExponent(), 0);

        // verify that adding PolyUnits with different exponents throws an error
        try {
            new PolyUnit(1, 3).add(new PolyUnit(2, 2));
            fail("expected exception: DifferentOrderMonomialException");
        }
        catch (DifferentOrderMonomialException e)
        {
            // success
        }
    }

    @Test
    public void subtractPolyUnit()
    {
        PolyUnit p1 = new PolyUnit(3, 3);
        PolyUnit p2 = new PolyUnit(2, 3);
        PolyUnit result = p1.subtract(p2);

        // assert the original PolyUnits remained unchanged
        assertTrue(new PolyUnit(3, 3).equals(p1));
        assertTrue(new PolyUnit(2, 3).equals(p2));

        assertEquals(1, result.getCoefficient(), 0);
        assertEquals(3, result.getExponent(), 0);

        // verify subtracting a negative PolyUnit
        result = new PolyUnit(1, 2).subtract(new PolyUnit(-2, 2));
        assertEquals(3, result.getCoefficient(), 0);
        assertEquals(2, result.getExponent(), 0);

        // assert that subtracting 0 doesn't change the PolyUnit
        // assert that constructing a PolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new PolyUnit(2, 0).subtract(new PolyUnit(0, 1));
        assertEquals(2, result.getCoefficient(), 0);
        assertEquals(0, result.getExponent(), 0);

        // verify that subtracting PolyUnits with different exponents throws an error
        try {
            new PolyUnit(1, 3).subtract(new PolyUnit(2, 2));
            fail("expected exception: DifferentOrderMonomialException");
        }
        catch (DifferentOrderMonomialException e)
        {
            // success
        }
    }

    @Test
    public void multiplyPolyUnit()
    {
        PolyUnit p1 = new PolyUnit(2, 3);
        PolyUnit p2 = new PolyUnit(3, 3);
        PolyUnit result = p1.multiply(p2);

        // assert the original PolyUnits remained unchanged
        assertTrue(new PolyUnit(2, 3).equals(p1));
        assertTrue(new PolyUnit(3, 3).equals(p2));

        assertEquals(6, result.getCoefficient(), 0);
        assertEquals(6, result.getExponent(), 0);

        // verify multiplying a negative PolyUnit
        result = new PolyUnit(1, 2).multiply(new PolyUnit(-2, 2));
        assertEquals(-2, result.getCoefficient(), 0);
        assertEquals(4, result.getExponent(), 0);

        // assert that multiplying by 1 doesn't change the PolyUnit
        result = new PolyUnit(3, 4).multiply(new PolyUnit(1, 0));
        assertEquals(3, result.getCoefficient(), 0);
        assertEquals(4, result.getExponent(), 0);

        // assert that multiplying by 0 doesn't yields 0
        // assert that constructing a PolyUnit with a 0 coefficient makes the
        //   exponent 0
        result = new PolyUnit(2, 0).multiply(new PolyUnit(0, 1));
        assertEquals(0, result.getCoefficient(), 0);
        assertEquals(0, result.getExponent(), 0);
    }

    @Test
    public void dividePolyUnit()
    {
        PolyUnit p1 = new PolyUnit(2, 3);
        PolyUnit p2 = new PolyUnit(3, 3);
        PolyUnit result = p1.divide(p2);

        // assert the original PolyUnits remained unchanged
        assertTrue(new PolyUnit(2, 3).equals(p1));
        assertTrue(new PolyUnit(3, 3).equals(p2));

        assertEquals(((double) 2 / 3), result.getCoefficient(), 0);
        assertEquals(0, result.getExponent(), 0);

        // verify dividing a negative PolyUnit
        result = new PolyUnit(1, 2).divide(new PolyUnit(-2, 2));
        assertEquals(((double) -1 / 2), result.getCoefficient(), 0);
        assertEquals(0, result.getExponent(), 0);

        // assert that dividing by 1 doesn't change the PolyUnit
        result = new PolyUnit(2, 3).divide(new PolyUnit(1, 0));
        assertEquals(2, result.getCoefficient(), 0);
        assertEquals(3, result.getExponent(), 0);

        // verify that dividing by 0 throws an error
        try {
            new PolyUnit(1, 3).divide(new PolyUnit(0, 0));
            fail("expected exception: ZeroDivisionException");
        }
        catch (ZeroDivisionException e)
        {
            // success
        }
    }

    @Test
    public void evalPolyUnit()
    {
        PolyUnit p = new PolyUnit(2, 3);
        double result = p.eval(3);

        // assert the original PolyUnit remains unchanged
        assertTrue(new PolyUnit(2, 3).equals(p));

        assertEquals(54, result, 0);

        // verify evaluating at a negative number
        result = new PolyUnit(3, 2).eval(-2);
        assertEquals(12, result, 0);

        result = new PolyUnit(3, 3).eval(-2);
        assertEquals(-24, result, 0);

        // assert that evaluating at 1 returns the coefficient
        result = new PolyUnit(3, 2).eval(1);
        assertEquals(3, result, 0);

        // assert that evaluating at 0 returns 0
        result = new PolyUnit(2, 2).eval(0);
        assertEquals(0, result, 0);
    }

    @Test
    public void derivePolyUnit()
    {
        PolyUnit p = new PolyUnit(2, 3);
        PolyUnit result = p.derivative();

        // assert the original PolyUnit remains unchanged
        assertTrue(new PolyUnit(2, 3).equals(p));

        assertEquals(new PolyUnit(6, 2), result);

        // verify exponent of value 1
        result = new PolyUnit(2, 1).derivative();
        assertEquals(new PolyUnit(2, 0), result);

        // verify exponent of value 0 yields 0
        result = new PolyUnit(2, 0).derivative();
        assertEquals(new PolyUnit(0, 0), result);

        // verify negative exponent
        result = new PolyUnit(2, -1).derivative();
        assertEquals(new PolyUnit(-2, -2), result);

        result = new PolyUnit(-3, -2).derivative();
        assertEquals(new PolyUnit(6, -3), result);
    }

    @Test
    public void antiDerivePolyUnit()
    {
        PolyUnit p = new PolyUnit(2, 3);
        PolyUnit result = p.antiDerivative();

        // assert the original PolyUnit remains unchanged
        assertTrue(new PolyUnit(2, 3).equals(p));

        assertEquals(new PolyUnit(((double) 1 / 2), 4), result);

        // verify exponent of value 1
        result = new PolyUnit(2, 1).antiDerivative();
        assertEquals(new PolyUnit(1, 2), result);

        // verify exponent of value 0
        result = new PolyUnit(2, 0).antiDerivative();
        assertEquals(new PolyUnit(2, 1), result);

        // verify negative exponent
        result = new PolyUnit(-3, -2).antiDerivative();
        assertEquals(new PolyUnit(3, -1), result);
    }

    @Test
    public void integratePolyUnit()
    {
        PolyUnit p = new PolyUnit(6, 2);
        double result = p.integral(0, 3);

        // assert the original PolyUnit remains unchanged
        assertTrue(new PolyUnit(6, 2).equals(p));

        assertEquals(54, result, 0);

        // verify integrating with reversed bounds yields the negative of the result
        result = new PolyUnit(6, 2).integral(3, 0);
        assertEquals(-54, result, 0);

        // assert that integrating over a range of size 0 returns 0
        result = new PolyUnit(2, 2).integral(5, 5);
        assertEquals(0, result, 0);

        // verify that integrating with non-zero bounds works
        result = new PolyUnit(1, 2).integral(3, 6);
        assertEquals(63, result, 0);
    }
}
