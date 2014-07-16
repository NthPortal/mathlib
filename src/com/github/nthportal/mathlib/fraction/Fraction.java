package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class Fraction implements Comparable<Fraction>
{
	private int numer;
	private int denom;

	private Fraction()
	{
	}

	
	/**
	 * @param numer An int numerator of the fraction.
	 * @param denom An int denominator of the fraction. Should not be 0.
	 */
	public Fraction(int numer, int denom)
	{
		if (denom == 0)
		{
			throw new ZeroDenomException();
		}

		this.numer = numer;
		this.denom = denom;
		this.fixNegativeDenom();
		this.reduce();
	}

	
	/**
	 * @param whole An int used as the value of the fraction.
	 * The denominator of the fraction is set to 1.
	 */
	public Fraction(int whole)
	{
		this.numer = whole;
		this.denom = 1;
	}

	/**
	 * @param frac A fraction to be copied. 
	 */
	public Fraction(Fraction frac)
	{
		this.numer = frac.numer;
		this.denom = frac.denom;
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

	private void reduce()
	{
		int gcf = Algebra.gcf(this.numer, this.denom);

		this.numer /= gcf;
		this.denom /= gcf;
	}

	private void fixNegativeDenom()
	{
		if (this.denom < 0)
		{
			this.denom *= -1;
			this.numer *= -1;
		}
	}

	public Fraction reciprocal()
	{
		if (this.numer == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		Fraction reciprocal = new Fraction(this.denom, this.numer);
		reciprocal.fixNegativeDenom();
		return reciprocal;
	}

	public Fraction add(Fraction f)
	{
		Fraction result = new Fraction();

		int gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				+ (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public Fraction add(int num)
	{
		return this.add(new Fraction(num));
	}

	public Fraction subtract(Fraction f)
	{
		Fraction result = new Fraction();

		int gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				- (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public Fraction subtract(int num)
	{
		return this.subtract(new Fraction(num));
	}

	public Fraction multiply(Fraction f)
	{
		Fraction result = new Fraction();

		result.numer = (this.numer * f.numer);
		result.denom = (this.denom * f.denom);

		result.reduce();

		return result;
	}

	public Fraction multiply(int scalar)
	{
		return new Fraction((this.numer * scalar), this.denom);
	}

	public Fraction divide(Fraction f)
	{
		Fraction result = new Fraction();

		if (f.numer == 0)
		{
			throw new ZeroDivisionException(
					"Can't divide by a fraction with a '0' numerator.");
		}

		result.numer = (this.numer * f.denom);
		result.denom = (this.denom * f.numer);

		result.fixNegativeDenom();

		result.reduce();

		return result;
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
		Fraction result = new Fraction(this);

		if (this.numer == 0)
		{
			return result;
		}

		if (exponent < 0)
		{
			exponent *= -1;
			result = result.reciprocal();
		}

		result.numer = (int) Math.pow(result.numer, exponent);
		result.denom = (int) Math.pow(result.denom, exponent);

		return result;
	}

	public Fraction pow(Fraction exponent) throws NonIntFractionException
	{
		Fraction result = new Fraction(this);

		if (this.numer != 0)
		{
			if (exponent.denom != 1)
			{
				throw new NonIntFractionException(
						"Fractional exponents with non-'1' denominators not permitted.");
			}

			if (exponent.numer < 0)
			{
				exponent.multiply(-1);
				result = result.reciprocal();
			}

			result.numer = (int) Math.pow(result.numer, exponent.numer);
			result.denom = (int) Math.pow(result.denom, exponent.numer);
		}

		return result;
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

	public static void main(String[] args)
	{
	}
}
