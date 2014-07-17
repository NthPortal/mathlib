package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class Fraction implements Comparable<Fraction>
{
	private final int numer;
	private final int denom;

	/**
	 * @param numer
	 *            An int numerator of the fraction.
	 * @param denom
	 *            An int denominator of the fraction. Should not be 0.
	 */
	public Fraction(int numer, int denom)
	{
		if (denom == 0)
		{
			throw new ZeroDenomException();
		}

		if (denom < 0)
		{
			denom *= -1;
			numer *= -1;
		}

		int gcf = Algebra.gcf(numer, denom);

		this.numer = numer / gcf;
		this.denom = denom / gcf;
	}

	/**
	 * @param whole
	 *            An int used as the value of the fraction. The denominator of
	 *            the fraction is set to 1.
	 */
	public Fraction(int whole)
	{
		this(whole, 1);
	}

	public int getNumer()
	{
		return this.numer;
	}

	public int getDenom()
	{
		return this.denom;
	}

	public boolean isInt()
	{
		if (this.denom == 1)
		{
			return true;
		}
		// Else
		return false;
	}

	public int toInt() throws NonIntFractionException
	{
		if (this.denom != 1)
		{
			throw new NonIntFractionException(
					"Cannot convert Fraction with non-'1' denominator to int.");
		}

		return this.numer;
	}

	public double toDouble()
	{
		return (this.numer / (double) this.denom);
	}

	public Fraction reciprocal()
	{
		if (this.numer == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		Fraction reciprocal = new Fraction(this.denom, this.numer);
		return reciprocal;
	}

	public Fraction add(Fraction f)
	{
		int gcf = Algebra.gcf(this.denom, f.denom);

		int denom = (this.denom * f.denom / gcf);
		int numer = (this.numer * f.denom / gcf) + (f.numer * this.denom / gcf);

		return new Fraction(numer, denom);
	}

	public Fraction add(int num)
	{
		return this.add(new Fraction(num));
	}

	public Fraction subtract(Fraction f)
	{
		int gcf = Algebra.gcf(this.denom, f.denom);

		int denom = (this.denom * f.denom / gcf);
		int numer = (this.numer * f.denom / gcf) - (f.numer * this.denom / gcf);

		return new Fraction(numer, denom);
	}

	public Fraction subtract(int num)
	{
		return this.subtract(new Fraction(num));
	}

	public Fraction multiply(Fraction f)
	{
		int numer = (this.numer * f.numer);
		int denom = (this.denom * f.denom);

		return new Fraction(numer, denom);
	}

	public Fraction multiply(int scalar)
	{
		return new Fraction((this.numer * scalar), this.denom);
	}

	public Fraction divide(Fraction f)
	{
		if (f.numer == 0)
		{
			throw new ZeroDivisionException(
					"Can't divide by a fraction with a '0' numerator.");
		}

		int numer = (this.numer * f.denom);
		int denom = (this.denom * f.numer);

		return new Fraction(numer, denom);
	}

	public Fraction divide(int scalar)
	{
		if (scalar == 0)
		{
			throw new ZeroDivisionException("Cannot divide by 0.");
		}

		return new Fraction(this.numer, (this.denom * scalar));
	}

	public Fraction pow(int exponent)
	{
		if (this.numer == 0)
		{
			return this;
		}

		Fraction temp = this;

		if (exponent < 0)
		{
			exponent *= -1;
			temp = temp.reciprocal();
		}

		int numer = (int) Math.pow(temp.numer, exponent);
		int denom = (int) Math.pow(temp.denom, exponent);

		return new Fraction(numer, denom);
	}

	public int compareTo(Fraction frac)
	{
		int gcf = Algebra.gcf(this.denom, frac.denom);

		if ((this.numer * frac.denom / gcf) < (frac.numer * this.denom / gcf))
		{
			return -1;
		}
		else if ((this.numer * frac.denom / gcf) == (frac.numer * this.denom / gcf))
		{
			return 0;
		}
		// Else
		return 1;
	}

	public int compareTo(int num)
	{
		if (this.numer < (num * this.denom))
		{
			return -1;
		}
		else if (this.numer == (num * this.denom))
		{
			return 0;
		}
		// Else
		return 1;
	}

	public boolean equals(Fraction frac)
	{
		if ((this.numer == frac.numer) && (this.denom == frac.denom))
		{
			return true;
		}
		// Else
		return false;
	}

	public void print()
	{
		System.out.print(this.numer + "/" + this.denom);
	}

	public void println()
	{
		System.out.println(this.numer + "/" + this.denom);
	}
}
