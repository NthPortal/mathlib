package com.github.nthportal.mathlib.polynomial;

class PolyUnit implements Comparable<PolyUnit>
{
	private static final String VAR_SYMBOL = "x";
    private static final int HASH_PRIME_1 = 19;
    private static final int HASH_PRIME_2 = 61;
    private static final int PRECISION_FACTOR = 100;

	private double coefficient;
	private int exponent;

	public PolyUnit()
	{
	}

	public PolyUnit(double coefficient, int exponent)
	{
		if (this.exponent < 0)
		{
			throw new InvalidExponentException(
					"Something went wrong. This shouldn't have been called.");
		}

		this.coefficient = coefficient;

		if (coefficient == 0)
		{
			this.exponent = 0;
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public PolyUnit(PolyUnit p)
	{
		this.coefficient = p.coefficient;
		this.exponent = p.exponent;
	}

	public double getCoefficient()
	{
		return this.coefficient;
	}

	public int getExponent()
	{
		return this.exponent;
	}

	public PolyUnit add(PolyUnit p) throws DifferentOrderMonomialException
	{
		if (this.exponent != p.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new PolyUnit((this.coefficient + p.coefficient), this.exponent);
	}

	public PolyUnit subtract(PolyUnit p) throws DifferentOrderMonomialException
	{
		if (this.exponent != p.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new PolyUnit((this.coefficient - p.coefficient), this.exponent);
	}

	public PolyUnit multiply(PolyUnit p)
	{
		return new PolyUnit((this.coefficient * p.coefficient),
				(this.exponent + p.exponent));
	}

	public PolyUnit multiply(double scalar)
	{
		return new PolyUnit((this.coefficient * scalar), this.exponent);
	}

	public PolyUnit divide(PolyUnit p)
	{
		return new PolyUnit((this.coefficient / p.coefficient),
				(this.exponent - p.exponent));
	}

	public PolyUnit divide(double scalar)
	{
		return new PolyUnit((this.coefficient / scalar), this.exponent);
	}

	// Compares the order of the monomial, not a value
	public int compareTo(PolyUnit p)
	{
		if (this.exponent < p.exponent)
		{
			return -1;
		}
		else if (this.exponent == p.exponent)
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

        if (!(obj instanceof PolyUnit)) {
            return false;  // takes care of null check
        }

        PolyUnit that = (PolyUnit) obj;

        return ((this.coefficient == that.coefficient) && (this.exponent == that.exponent));
    }

    public int hashCode()
    {
        int result = HASH_PRIME_1;
        result = HASH_PRIME_2 * result + (int) (this.coefficient * PRECISION_FACTOR);
        result = HASH_PRIME_2 * result + this.exponent;
        return result;
    }

	public double eval(double value)
	{
		return (this.coefficient * Math.pow(value, this.exponent));
	}

	public PolyUnit derivative()
	{
		return new PolyUnit((this.coefficient * this.exponent),
				(this.exponent - 1));
	}

	public PolyUnit antiDerivative()
	{
		return new PolyUnit((this.coefficient / (this.exponent + 1)),
				(this.exponent + 1));
	}

	public double integral(double lowerBound, double upperBound)
	{
		PolyUnit antiDeriv = this.antiDerivative();
		return (antiDeriv.eval(upperBound) - antiDeriv.eval(lowerBound));
	}

	public void print()
	{
		System.out.print(this.coefficient + VAR_SYMBOL + "^(" + this.exponent
				+ ")");
	}

	public void println()
	{
		System.out.println(this.coefficient + VAR_SYMBOL + "^(" + this.exponent
				+ ")");
	}
}