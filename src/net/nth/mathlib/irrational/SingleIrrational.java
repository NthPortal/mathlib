package net.nth.mathlib.irrational;

import net.nth.mathlib.fraction.IrrationalFraction;

public class SingleIrrational
{
	private int num;
	private IrrationalFraction exponent;

	public SingleIrrational()
	{
	}

	public SingleIrrational(int num)
	{
		this.num = num;
		this.exponent = new IrrationalFraction(1);
	}

	public SingleIrrational(int num, int exp)
	{
		this.num = num;
		this.exponent = new IrrationalFraction(exp);
	}

	public SingleIrrational(int num, IrrationalFraction exp)
	{
		this.num = num;
		this.exponent = new IrrationalFraction(exp);
	}

	public int getNum()
	{
		return this.num;
	}

	public IrrationalFraction getExponent()
	{
		return new IrrationalFraction(this.exponent);
	}

	public SingleIrrational add(SingleIrrational m) throws DifferentPowerException
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot add numbers with different exponents.");
		}

		return new SingleIrrational((this.num + m.num), this.exponent);
	}

	public SingleIrrational add(int scalar)
	{
		return this.add(new SingleIrrational(scalar));
	}

	public SingleIrrational subtract(SingleIrrational m) throws DifferentPowerException
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot subtract numbers with different exponents.");
		}

		return new SingleIrrational((this.num - m.num), this.exponent);
	}

	public SingleIrrational subtract(int scalar)
	{
		return this.subtract(new SingleIrrational(scalar));
	}

	public SingleIrrational multiply(SingleIrrational m)
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot multiply numbers with different exponents.");
		}

		return new SingleIrrational((this.num * m.num), this.exponent);
	}

	public SingleIrrational multiply(int scalar)
	{
		return this.multiply(new SingleIrrational(scalar));
	}

	public SingleIrrational divide(SingleIrrational m)
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot divide numbers with different exponents.");
		}

		return new SingleIrrational((this.num / m.num), this.exponent);
	}

	public SingleIrrational divide(int scalar)
	{
		return this.divide(new SingleIrrational(scalar));
	}
}
