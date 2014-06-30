package net.nth.mathlib.polynomial;

import net.nth.mathlib.fraction.Fraction;

public class FractionMonomial
{
	private Fraction coefficient;
	private Fraction exponent;

	public FractionMonomial()
	{
	}

	public FractionMonomial(Fraction coefficient, Fraction exponent)
	{
		this.coefficient = coefficient;
		if (coefficient.compare(0))
		{
			this.exponent = new Fraction(0);
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public FractionMonomial(FractionMonomial m)
	{
		this.coefficient = m.coefficient;
		this.exponent = m.exponent;
	}

	public Fraction getCoefficient()
	{
		return this.coefficient;
	}

	public Fraction getExponent()
	{
		return this.exponent;
	}

	public static FractionMonomial add(FractionMonomial m1, FractionMonomial m2)
			throws DifferentOrderMonomialException
	{
		if (m1.exponent != m2.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new FractionMonomial(m1.exponent, Fraction.add(m1.coefficient,
				m2.coefficient));
	}

	public static FractionMonomial subtract(FractionMonomial m1,
			FractionMonomial m2) throws DifferentOrderMonomialException
	{
		if (m1.exponent != m2.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new FractionMonomial(m1.exponent, Fraction.subtract(
				m1.coefficient, m2.coefficient));
	}

	public static FractionMonomial multiply(FractionMonomial m1,
			FractionMonomial m2)
	{
		return new FractionMonomial(Fraction.multiply(m1.coefficient,
				m2.coefficient), Fraction.add(m1.exponent, m2.exponent));
	}

	public FractionMonomial multiply(Fraction frac)
	{
		return new FractionMonomial(Fraction.multiply(this.coefficient, frac),
				this.exponent);
	}

	public FractionMonomial multiply(int scalar)
	{
		return new FractionMonomial(this.coefficient.multiply(scalar),
				this.exponent);
	}

	public static FractionMonomial divide(FractionMonomial m1,
			FractionMonomial m2)
	{
		return new FractionMonomial(Fraction.divide(m1.coefficient,
				m2.coefficient), Fraction.subtract(m1.exponent, m2.exponent));
	}
	
	public FractionMonomial divide(Fraction frac)
	{
		return new FractionMonomial(Fraction.divide(this.coefficient, frac),
				this.exponent);
	}

	public FractionMonomial divide(int scalar)
	{
		return new FractionMonomial(this.coefficient.divide(scalar),
				this.exponent);
	}

	public FractionMonomial derivative()
	{
		return new FractionMonomial(Fraction.multiply(this.coefficient,
				this.exponent), this.exponent.subtract(1));
	}

	public FractionMonomial integral()
	{
		return new FractionMonomial(Fraction.divide(this.coefficient,
				this.exponent.add(1)), this.exponent.add(1));
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		Fraction result = new Fraction(0);
		result = Fraction.add(
				result,
				Fraction.multiply(this.coefficient,
						upperBound.pow(this.exponent)));
		result = Fraction.subtract(
				result,
				Fraction.multiply(this.coefficient,
						lowerBound.pow(this.exponent)));
		return result;
	}
}