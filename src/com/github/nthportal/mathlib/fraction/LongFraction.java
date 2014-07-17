package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class LongFraction
{
	private final long numer;
	private final long denom;

	/**
	 * @param numer
	 *            A long numerator of the LongFraction.
	 * @param denom
	 *            A long denominator of the LongFraction. Should not be 0.
	 */
	public LongFraction(long numer, long denom)
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

		long gcf = Algebra.gcf(numer, denom);

		this.numer = numer / gcf;
		this.denom = denom / gcf;
	}

	/**
	 * @param whole
	 *            A long used as the value of the LongFraction. The denominator
	 *            of the LongFraction is set to 1.
	 */
	public LongFraction(long whole)
	{
		this(whole, 1);
	}

	public long getNumer()
	{
		return this.numer;
	}

	public long getDenom()
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

	public long toInt() throws NonIntFractionException
	{
		if (this.denom != 1)
		{
			throw new NonIntFractionException(
					"Cannot convert LongFraction with non-'1' denominator to long.");
		}

		return this.numer;
	}

	public double toDouble()
	{
		return (this.numer / (double) this.denom);
	}

	public LongFraction reciprocal()
	{
		if (this.numer == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		LongFraction reciprocal = new LongFraction(this.denom, this.numer);
		return reciprocal;
	}

	public LongFraction add(LongFraction f)
	{
		long gcf = Algebra.gcf(this.denom, f.denom);

		long denom = (this.denom * f.denom / gcf);
		long numer = (this.numer * f.denom / gcf)
				+ (f.numer * this.denom / gcf);

		return new LongFraction(numer, denom);
	}

	public LongFraction add(long num)
	{
		return this.add(new LongFraction(num));
	}

	public LongFraction subtract(LongFraction f)
	{
		long gcf = Algebra.gcf(this.denom, f.denom);

		long denom = (this.denom * f.denom / gcf);
		long numer = (this.numer * f.denom / gcf)
				- (f.numer * this.denom / gcf);

		return new LongFraction(numer, denom);
	}

	public LongFraction subtract(long num)
	{
		return this.subtract(new LongFraction(num));
	}

	public LongFraction multiply(LongFraction f)
	{
		long numer = (this.numer * f.numer);
		long denom = (this.denom * f.denom);

		return new LongFraction(numer, denom);
	}

	public LongFraction multiply(long scalar)
	{
		return new LongFraction((this.numer * scalar), this.denom);
	}

	public LongFraction divide(LongFraction f)
	{
		if (f.numer == 0)
		{
			throw new ZeroDivisionException(
					"Can't divide by a LongFraction with a '0' numerator.");
		}

		long numer = (this.numer * f.denom);
		long denom = (this.denom * f.numer);

		return new LongFraction(numer, denom);
	}

	public LongFraction divide(long scalar)
	{
		if (scalar == 0)
		{
			throw new ZeroDivisionException("Cannot divide by 0.");
		}

		return new LongFraction(this.numer, (this.denom * scalar));
	}

	public LongFraction pow(long exponent)
	{
		if (this.numer == 0)
		{
			return this;
		}

		LongFraction temp = this;

		if (exponent < 0)
		{
			exponent *= -1;
			temp = temp.reciprocal();
		}

		long numer = (long) Math.pow(temp.numer, exponent);
		long denom = (long) Math.pow(temp.denom, exponent);

		return new LongFraction(numer, denom);
	}

	public long compareTo(LongFraction frac)
	{
		long gcf = Algebra.gcf(this.denom, frac.denom);

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

	public long compareTo(long num)
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

	public boolean equals(LongFraction frac)
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
