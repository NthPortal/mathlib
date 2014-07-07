package com.github.nthportal.mathlib.irrational;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.fraction.NonIntFractionException;
import com.github.nthportal.mathlib.fraction.ZeroDenomException;

public class IrrationalFraction implements Comparable<IrrationalFraction>
{
	private Radical numer;
	private Radical denom;

	public IrrationalFraction()
	{
	}

	public IrrationalFraction(Radical numer, Radical denom)
	{
		if (denom.compareTo(0) == 0)
		{
			throw new ZeroDenomException();
		}

		this.numer = new Radical(numer);
		this.denom = new Radical(denom);
		this.reduce();
	}

	public IrrationalFraction(Radical r)
	{
		this.numer = new Radical(r);
		this.denom = new Radical(1);
	}

	public IrrationalFraction(IrrationalFraction frac)
	{
		this.numer = frac.numer;
		this.denom = frac.denom;
	}

	public Radical getNumer()
	{
		return new Radical(this.numer);
	}

	public Radical getDenom()
	{
		return new Radical(this.denom);
	}

	public double toDouble()
	{
		return (this.numer.toDouble() / this.denom.toDouble());
	}

	private void reduce() // NEEDS REWRITING
	{
		throw new RuntimeException("METHOD NOT YET WRITTERN");
	}

	private void fixNegativeDenom()
	{
		if (this.denom.compareTo(0) == -1)
		{
			this.denom.multiply(-1);
			this.numer.multiply(-1);
		}
	}

	public IrrationalFraction reciprocal()
	{
		if (this.numer.compareTo(0) == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		IrrationalFraction reciprocal = new IrrationalFraction(this.denom,
				this.numer);
		reciprocal.fixNegativeDenom();
		return reciprocal;
	}

	public IrrationalFraction multiply(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		result.numer = this.numer.multiply(f.numer);
		result.denom = this.denom.multiply(f.denom);

		result.reduce();

		return result;
	}

	public IrrationalFraction multiply(int scalar)
	{
		return new IrrationalFraction(this.numer.multiply(scalar), this.denom);
	}

	public IrrationalFraction divide(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		if (f.numer.compareTo(0) == 0)
		{
			throw new ZeroDenomException(
					"Can't divide by a fraction with a '0' numerator.");
		}

		result.numer = this.numer.multiply(f.denom);
		result.denom = this.denom.multiply(f.numer);

		result.fixNegativeDenom();

		result.reduce();

		return result;
	}

	public IrrationalFraction divide(int scalar)
	{
		if (scalar == 0)
		{
			throw new ZeroDenomException("Cannot divide by 0.");
		}

		return new IrrationalFraction(this.numer, this.denom.multiply(scalar));
	}

	public IrrationalFraction pow(int exponent)
	{
		IrrationalFraction result = new IrrationalFraction(this);

		if (this.numer.compareTo(0) == 0)
		{
			return result;
		}

		if (exponent < 0)
		{
			exponent *= -1;
			result = result.reciprocal();
		}

		result.numer = result.numer.pow(exponent);
		result.denom = result.denom.pow(exponent);

		return result;
	}

	public IrrationalFraction pow(Fraction exponent)
			throws NonIntFractionException
	{
		IrrationalFraction result = new IrrationalFraction(this);

		if (this.numer.compareTo(0) != 0)
		{
			if (exponent.compare(0) == -1)
			{
				exponent.multiply(-1);
				result = result.reciprocal();
			}

			result.numer = result.numer.pow(exponent);
			result.denom = result.denom.pow(exponent);
		}

		return result;
	}

	public int compareTo(IrrationalFraction frac)
	{
		if ((this.numer.toDouble() * frac.denom.toDouble()) < (frac.numer
				.toDouble() * this.denom.toDouble()))
		{
			return -1;
		}
		else if ((this.numer.toDouble() * frac.denom.toDouble()) == (frac.numer
				.toDouble() * this.denom.toDouble()))
		{
			return 0;
		}
		// Else
		return 1;
	}

	public int compare(int num)
	{
		return this.numer.compareTo(this.denom.multiply(num));
	}

	public void print()
	{
		System.out.print(this.numer + "/" + this.denom);
	}

	public void println()
	{
		System.out.println(this.numer + "/" + this.denom);
	}

	public static void main(String[] args)
	{
	}
}