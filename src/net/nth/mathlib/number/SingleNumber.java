package net.nth.mathlib.number;

import net.nth.mathlib.fraction.IrrationalFraction;

public class SingleNumber
{
	private int num;
	private IrrationalFraction exponent;

	public SingleNumber()
	{
	}

	public SingleNumber(int num)
	{
		this.num = num;
		this.exponent = new IrrationalFraction(1);
	}

	public SingleNumber(int num, int exp)
	{
		this.num = num;
		this.exponent = new IrrationalFraction(exp);
	}

	public SingleNumber(int num, IrrationalFraction exp)
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

	public SingleNumber add(SingleNumber m) throws DifferentPowerException
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot add numbers with different exponents.");
		}

		return new SingleNumber((this.num + m.num), this.exponent);
	}

	public SingleNumber add(int scalar)
	{
		return this.add(new SingleNumber(scalar));
	}

	public SingleNumber subtract(SingleNumber m) throws DifferentPowerException
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot subtract numbers with different exponents.");
		}

		return new SingleNumber((this.num - m.num), this.exponent);
	}

	public SingleNumber subtract(int scalar)
	{
		return this.subtract(new SingleNumber(scalar));
	}

	public SingleNumber multiply(SingleNumber m)
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot multiply numbers with different exponents.");
		}

		return new SingleNumber((this.num * m.num), this.exponent);
	}

	public SingleNumber multiply(int scalar)
	{
		return this.multiply(new SingleNumber(scalar));
	}

	public SingleNumber divide(SingleNumber m)
	{
		if (this.exponent.compareTo(m.exponent) != 0)
		{
			throw new DifferentPowerException(
					"Cannot divide numbers with different exponents.");
		}

		return new SingleNumber((this.num / m.num), this.exponent);
	}

	public SingleNumber divide(int scalar)
	{
		return this.divide(new SingleNumber(scalar));
	}
}
