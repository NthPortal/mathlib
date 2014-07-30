package com.github.nthportal.mathlib.polynomial;

import com.github.nthportal.mathlib.fraction.Fraction;

class FracPolyUnit implements Comparable<FracPolyUnit>
{
	private static final String VAR_SYMBOL = "x";
    private static final int HASH_PRIME_1 = 29;
    private static final int HASH_PRIME_2 = 43;

	private Fraction coefficient;
	private int exponent;

	public FracPolyUnit()
	{
	}

	public FracPolyUnit(Fraction coefficient, int exponent)
	{
		if (this.exponent < 0)
		{
			throw new InvalidExponentException(
					"Something went wrong. This shouldn't have been called.");
		}

		this.coefficient = new Fraction(coefficient);

		if (coefficient.compareTo(0) == 0)
		{
			this.exponent = 0;
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public FracPolyUnit(int coefficient, int exponent)
	{
		if (this.exponent < 0)
		{
			throw new InvalidExponentException(
					"Something went wrong. This shouldn't have been called.");
		}

		this.coefficient = new Fraction(coefficient);

		if (coefficient == 0)
		{
			this.exponent = 0;
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public FracPolyUnit(FracPolyUnit m)
	{
		this.coefficient = new Fraction(m.coefficient);
		this.exponent = m.exponent;
	}

	public Fraction getCoefficient()
	{
		return new Fraction(this.coefficient);
	}

	public int getExponent()
	{
		return this.exponent;
	}

	public FracPolyUnit add(FracPolyUnit m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new FracPolyUnit(this.coefficient.add(m.coefficient),
				this.exponent);
	}

	public FracPolyUnit subtract(FracPolyUnit m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new FracPolyUnit(this.coefficient.subtract(m.coefficient),
				this.exponent);
	}

	public FracPolyUnit multiply(FracPolyUnit m)
	{
		return new FracPolyUnit(this.coefficient.multiply(m.coefficient),
				(this.exponent + m.exponent));
	}

	public FracPolyUnit multiply(Fraction frac)
	{
		return new FracPolyUnit(this.coefficient.multiply(frac),
				this.exponent);
	}

	public FracPolyUnit multiply(int scalar)
	{
		return new FracPolyUnit(this.coefficient.multiply(scalar),
				this.exponent);
	}

	public FracPolyUnit divide(FracPolyUnit m)
	{
		return new FracPolyUnit(this.coefficient.divide(m.coefficient),
				(this.exponent - m.exponent));
	}

	public FracPolyUnit divide(Fraction frac)
	{
		return new FracPolyUnit(this.coefficient.divide(frac),
				this.exponent);
	}

	public FracPolyUnit divide(int scalar)
	{
		return new FracPolyUnit(this.coefficient.divide(scalar),
				this.exponent);
	}

	public int compareTo(FracPolyUnit m)
	{
		if (this.exponent < m.exponent)
		{
			return -1;
		}
		else if (this.exponent == m.exponent)
		{
			return 0;
		}
		// Else
		return 1;
	}

    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FracPolyUnit)) {
            return false;  // takes care of null check
        }

        FracPolyUnit that = (FracPolyUnit) obj;

        return (this.coefficient.equals(that.coefficient) && (this.exponent == that.exponent));
    }

    public int hashCode()
    {
        int result = HASH_PRIME_1;
        result = HASH_PRIME_2 * result + this.coefficient.hashCode();
        result = HASH_PRIME_2 * result + this.exponent;
        return result;
    }

	public Fraction eval(int value)
	{
		return this.coefficient
				.multiply(new Fraction(value).pow(this.exponent));
	}

	public Fraction eval(Fraction value)
	{
		return this.coefficient.multiply(value.pow(this.exponent));
	}

	public FracPolyUnit derivative()
	{
		return new FracPolyUnit(this.coefficient.multiply(this.exponent),
				(this.exponent - 1));
	}

	public FracPolyUnit antiDerivative()
	{
		return new FracPolyUnit(this.coefficient.divide(this.exponent + 1),
				(this.exponent + 1));
	}

	public Fraction integral(int lowerBound, int upperBound)
	{
		FracPolyUnit antiDeriv = this.antiDerivative();
		return antiDeriv.eval(upperBound).subtract(antiDeriv.eval(lowerBound));
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		FracPolyUnit antiDeriv = this.antiDerivative();
		return antiDeriv.eval(upperBound).subtract(antiDeriv.eval(lowerBound));
	}

	public void print()
	{
		this.coefficient.print();
		System.out.print(VAR_SYMBOL + "^(" + this.exponent + ")");
	}

	public void println()
	{
		this.coefficient.print();
		System.out.println(VAR_SYMBOL + "^(" + this.exponent + ")");
	}
}