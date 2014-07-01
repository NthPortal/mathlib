package net.nth.mathlib.polynomial;

import net.nth.mathlib.fraction.Fraction;

public class FractionMonomial implements Comparable<FractionMonomial>
{
	private static final char VAR_SYMBOL = 'x';

	private Fraction coefficient;
	private Fraction exponent;

	public FractionMonomial()
	{
	}

	public FractionMonomial(Fraction coefficient, Fraction exponent)
	{
		this.coefficient = coefficient;
		if (coefficient.compare(0) == 0)
		{
			this.exponent = new Fraction(0);
		}
		else
		{
			this.exponent = exponent;
		}
	}

	public FractionMonomial(int coefficient, int exponent)
	{
		this.coefficient = new Fraction(coefficient);
		if (coefficient == 0)
		{
			this.exponent = new Fraction(0);
		}
		else
		{
			this.exponent = new Fraction(exponent);
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

	public FractionMonomial add(FractionMonomial m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot add monomials of different orders.");
		}

		return new FractionMonomial(this.exponent,
				this.coefficient.add(m.coefficient));
	}

	public FractionMonomial subtract(FractionMonomial m)
			throws DifferentOrderMonomialException
	{
		if (this.exponent != m.exponent)
		{
			throw new DifferentOrderMonomialException(
					"Cannot subtract monomials of different orders.");
		}

		return new FractionMonomial(this.exponent,
				this.coefficient.subtract(m.coefficient));
	}

	public FractionMonomial multiply(FractionMonomial m)
	{
		return new FractionMonomial(this.coefficient.multiply(m.coefficient),
				this.exponent.add(m.exponent));
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

	public FractionMonomial divideBy(FractionMonomial m)
	{
		return new FractionMonomial(this.coefficient.divideBy(m.coefficient),
				this.exponent.subtract(m.exponent));
	}

	public FractionMonomial divideBy(Fraction frac)
	{
		return new FractionMonomial(this.coefficient.divideBy(frac),
				this.exponent);
	}

	public FractionMonomial divideBy(int scalar)
	{
		return new FractionMonomial(this.coefficient.divideBy(scalar),
				this.exponent);
	}

	public int compareTo(FractionMonomial m)
	{
		return this.exponent.compareTo(m.exponent);
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
				this.exponent.subtract(1));
	}

	public FractionMonomial antiDerivative()
	{
		return new FractionMonomial(this.coefficient.divideBy(this.exponent
				.add(1)), this.exponent.add(1));
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
		System.out.print(VAR_SYMBOL + "^(");
		this.exponent.print();
		System.out.print(")");
	}

	public void println()
	{
		this.coefficient.print();
		System.out.print(VAR_SYMBOL + "^(");
		this.exponent.print();
		System.out.println(")");
	}
}