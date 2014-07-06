package net.nth.mathlib.polynomial;

class PolyUnit implements Comparable<PolyUnit>
{
	private static final String VAR_SYMBOL = "x";

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

	public PolyUnit(PolyUnit m)
	{
		this.coefficient = m.coefficient;
		this.exponent = m.exponent;
	}

	public double getCoefficient()
	{
		return this.coefficient;
	}

	public int getExponent()
	{
		return this.exponent;
	}

	public PolyUnit add(PolyUnit m) throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new PolyUnit((this.coefficient + m.coefficient), this.exponent);
	}

	public PolyUnit subtract(PolyUnit m) throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new PolyUnit((this.coefficient - m.coefficient), this.exponent);
	}

	public PolyUnit multiply(PolyUnit m)
	{
		return new PolyUnit((this.coefficient * m.coefficient),
				(this.exponent + m.exponent));
	}

	public PolyUnit multiply(double scalar)
	{
		return new PolyUnit((this.coefficient * scalar), this.exponent);
	}

	public PolyUnit divide(PolyUnit m)
	{
		return new PolyUnit((this.coefficient / m.coefficient),
				(this.exponent - m.exponent));
	}

	public PolyUnit divide(double scalar)
	{
		return new PolyUnit((this.coefficient / scalar), this.exponent);
	}

	// Compares the order of the monomial, not a value
	public int compareTo(PolyUnit m)
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