package net.nth.mathlib.polynomial;

import net.nth.mathlib.fraction.Fraction;

class FractionMonomial implements Comparable<FractionMonomial>
{
	private static final String VAR_SYMBOL = "x";

	private Fraction coefficient;
	private int exponent;

	public FractionMonomial()
	{
	}

	public FractionMonomial(Fraction coefficient, int exponent)
	{
		if (this.exponent < 0)
		{
			throw new InvalidExponentException(
					"Something went wrong. This shouldn't have been called.");
		}

		this.coefficient = new Fraction(coefficient);

		if (coefficient.compare(0) == 0)
		{
			this.exponent = 0;
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public FractionMonomial(int coefficient, int exponent)
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

	public FractionMonomial(FractionMonomial m)
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

	public FractionMonomial add(FractionMonomial m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new FractionMonomial(this.coefficient.add(m.coefficient),
				this.exponent);
	}

	public FractionMonomial subtract(FractionMonomial m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new FractionMonomial(this.coefficient.subtract(m.coefficient),
				this.exponent);
	}

	public FractionMonomial multiply(FractionMonomial m)
	{
		return new FractionMonomial(this.coefficient.multiply(m.coefficient),
				(this.exponent + m.exponent));
	}

	public FractionMonomial multiply(Fraction frac)
	{
		return new FractionMonomial(this.coefficient.multiply(frac),
				this.exponent);
	}

	public FractionMonomial multiply(int scalar)
	{
		return new FractionMonomial(this.coefficient.multiply(scalar),
				this.exponent);
	}

	public FractionMonomial divide(FractionMonomial m)
	{
		return new FractionMonomial(this.coefficient.divide(m.coefficient),
				(this.exponent - m.exponent));
	}

	public FractionMonomial divide(Fraction frac)
	{
		return new FractionMonomial(this.coefficient.divide(frac),
				this.exponent);
	}

	public FractionMonomial divide(int scalar)
	{
		return new FractionMonomial(this.coefficient.divide(scalar),
				this.exponent);
	}

	public int compareTo(FractionMonomial m)
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

	public Fraction eval(int value)
	{
		return this.coefficient
				.multiply(new Fraction(value).pow(this.exponent));
	}

	public Fraction eval(Fraction value)
	{
		return this.coefficient.multiply(value.pow(this.exponent));
	}

	public FractionMonomial derivative()
	{
		return new FractionMonomial(this.coefficient.multiply(this.exponent),
				(this.exponent - 1));
	}

	public FractionMonomial antiDerivative()
	{
		return new FractionMonomial(this.coefficient.divide(this.exponent + 1),
				(this.exponent + 1));
	}

	public Fraction integral(int lowerBound, int upperBound)
	{
		FractionMonomial antiDeriv = this.antiDerivative();
		return antiDeriv.eval(upperBound).subtract(antiDeriv.eval(lowerBound));
	}

	public Fraction integral(Fraction lowerBound, Fraction upperBound)
	{
		FractionMonomial antiDeriv = this.antiDerivative();
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