package net.nth.mathlib.polynomial;

public class Monomial
{
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

	public static Monomial add(Monomial m1, Monomial m2)
			throws DifferentOrderMonomialException
	{
		if (m1.exponent != m2.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new Monomial(m1.exponent, (m1.coefficient + m2.coefficient));
	}

	public static Monomial subtract(Monomial m1, Monomial m2)
			throws DifferentOrderMonomialException
	{
		if (m1.exponent != m2.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new Monomial(m1.exponent, (m1.coefficient - m2.coefficient));
	}

	public static Monomial multiply(Monomial m1, Monomial m2)
	{
		return new Monomial((m1.coefficient * m2.coefficient),
				(m1.exponent + m2.exponent));
	}
	
	public Monomial multiply(double scalar)
	{
		return new Monomial((this.coefficient * scalar), this.exponent);
	}

	public static Monomial divide(Monomial m1, Monomial m2)
	{
		return new Monomial((m1.coefficient / m2.coefficient),
				(m1.exponent - m2.exponent));
	}
	
	public Monomial divide(double scalar)
	{
		return new Monomial((this.coefficient / scalar), this.exponent);
	}

	public Monomial derivative()
	{
		return new Monomial((this.coefficient * this.exponent),
				(this.exponent - 1));
	}

	public Monomial integral()
	{
		return new Monomial((this.coefficient / (this.exponent + 1)),
				(this.exponent + 1));
	}

	public double integral(double lowerBound, double upperBound)
	{
		double result = 0;
		result += (this.coefficient * (Math.pow(upperBound, this.exponent)));
		result -= (this.coefficient * (Math.pow(lowerBound, this.exponent)));
		return result;
	}
}