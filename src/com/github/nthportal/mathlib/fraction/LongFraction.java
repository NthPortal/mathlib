package com.github.nthportal.mathlib.fraction;

import com.github.nthportal.mathlib.util.Algebra;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class LongFraction
{
    private static final int HASH_PRIME_1 = 19;
    private static final int HASH_PRIME_2 = 61;

	private long numer;
	private long denom;

	private LongFraction()
	{
	}

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

		this.numer = numer;
		this.denom = denom;
		this.fixNegativeDenom();
		this.reduce();
	}

	/**
	 * @param whole
	 *            A long used as the value of the LongFraction. The denominator of
	 *            the LongFraction is set to 1.
	 */
	public LongFraction(long whole)
	{
		this.numer = whole;
		this.denom = 1;
	}

	/**
	 * @param frac
	 *            A LongFraction to be copied.
	 */
	public LongFraction(LongFraction frac)
	{
		this.numer = frac.numer;
		this.denom = frac.denom;
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

	public long toLong() throws NonIntFractionException
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

	private void reduce()
	{
		long gcf = Algebra.gcf(this.numer, this.denom);

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

	public LongFraction reciprocal()
	{
		if (this.numer == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		LongFraction reciprocal = new LongFraction(this.denom, this.numer);
		reciprocal.fixNegativeDenom();
		return reciprocal;
	}

	public LongFraction add(LongFraction f)
	{
		LongFraction result = new LongFraction();

		long gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				+ (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public LongFraction add(long num)
	{
		return this.add(new LongFraction(num));
	}

	public LongFraction subtract(LongFraction f)
	{
		LongFraction result = new LongFraction();

		long gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				- (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public LongFraction subtract(long num)
	{
		return this.subtract(new LongFraction(num));
	}

	public LongFraction multiply(LongFraction f)
	{
		LongFraction result = new LongFraction();

		result.numer = (this.numer * f.numer);
		result.denom = (this.denom * f.denom);

		result.reduce();

		return result;
	}

	public LongFraction multiply(long scalar)
	{
		return new LongFraction((this.numer * scalar), this.denom);
	}

	public LongFraction divide(LongFraction f)
	{
		LongFraction result = new LongFraction();

		if (f.numer == 0)
		{
			throw new ZeroDivisionException(
					"Can't divide by a LongFraction with a '0' numerator.");
		}

		result.numer = (this.numer * f.denom);
		result.denom = (this.denom * f.numer);

		result.fixNegativeDenom();

		result.reduce();

		return result;
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
		LongFraction result = new LongFraction(this);

		if (this.numer == 0)
		{
			return result;
		}

		if (exponent < 0)
		{
			exponent *= -1;
			result = result.reciprocal();
		}

		result.numer = (long) Math.pow(result.numer, exponent);
		result.denom = (long) Math.pow(result.denom, exponent);

		return result;
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

	public boolean equals(Object obj)
	{
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LongFraction)) {
            return false;  // takes care of null check
        }

        LongFraction that = (LongFraction) obj;

        return ((this.numer == that.numer) && (this.denom == that.denom));
	}

    public int hashCode()
    {
        int result = HASH_PRIME_1;
        result = HASH_PRIME_2 * result + (int) this.numer;
        result = HASH_PRIME_2 * result + (int) this.denom;
        return result;
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
