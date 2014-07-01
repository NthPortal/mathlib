package net.nth.mathlib.polynomial;

public class Monomial implements Comparable<Monomial>
{
	private static final char VAR_SYMBOL = 'x';

	private double coefficient;
	private double exponent;

	public Monomial()
	{
	}

	public Monomial(double coefficient, double exponent)
	{
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

	public Monomial(Monomial m)
	{
		this.coefficient = m.coefficient;
		this.exponent = m.exponent;
	}

	public double getCoefficient()
	{
		return this.coefficient;
	}

	public double getExponent()
	{
		return this.exponent;
	}

	public Monomial add(Monomial m) throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new Monomial(this.exponent, (this.coefficient + m.coefficient));
	}

	public Monomial subtract(Monomial m) throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new Monomial(this.exponent, (this.coefficient - m.coefficient));
	}

	public Monomial multiply(Monomial m)
	{
		return new Monomial((this.coefficient * m.coefficient),
				(this.exponent + m.exponent));
	}

	public Monomial multiply(double scalar)
	{
		return new Monomial((this.coefficient * scalar), this.exponent);
	}

	public Monomial divideBy(Monomial m)
	{
		return new Monomial((this.coefficient / m.coefficient),
				(this.exponent - m.exponent));
	}

	public Monomial divideBy(double scalar)
	{
		return new Monomial((this.coefficient / scalar), this.exponent);
	}

	// Compares the order of the monomial, not a value
	public int compareTo(Monomial m)
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

	public Monomial derivative()
	{
		return new Monomial((this.coefficient * this.exponent),
				(this.exponent - 1));
	}

	public Monomial antiDerivative()
	{
		return new Monomial((this.coefficient / (this.exponent + 1)),
				(this.exponent + 1));
	}

	public double integral(double lowerBound, double upperBound)
	{
		Monomial antiDeriv = this.antiDerivative();
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