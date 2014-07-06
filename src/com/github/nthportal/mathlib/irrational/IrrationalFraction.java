package com.github.nthportal.mathlib.irrational;

import com.github.nthportal.mathlib.fraction.NonIntFractionException;
import com.github.nthportal.mathlib.fraction.ZeroDenomException;
import com.github.nthportal.mathlib.util.Algebra;

public class IrrationalFraction implements Comparable<IrrationalFraction>
{
	private int numer;
	private int denom;

	public IrrationalFraction()
	{
	}

	public IrrationalFraction(int numer, int denom)
	{
		if (denom == 0)
		{
			throw new ZeroDenomException();
		}

		this.numer = numer;
		this.denom = denom;
		this.reduce();
	}

	public IrrationalFraction(int whole)
	{
		this.numer = whole;
		this.denom = 1;
	}

	public IrrationalFraction(IrrationalFraction frac)
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
	
	public boolean testIfInt()
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

	public IrrationalFraction reciprocal()
	{
		if (this.numer == 0)
		{
			throw new ZeroDenomException("0 has no reciprocal.");
		}

		IrrationalFraction reciprocal = new IrrationalFraction(this.denom, this.numer);
		reciprocal.fixNegativeDenom();
		return reciprocal;
	}

	public IrrationalFraction add(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		int gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				+ (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public IrrationalFraction add(int num)
	{
		return this.add(new IrrationalFraction(num));
	}

	public IrrationalFraction subtract(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		int gcf = Algebra.gcf(this.denom, f.denom);

		result.denom = (this.denom * f.denom / gcf);
		result.numer = (this.numer * f.denom / gcf)
				- (f.numer * this.denom / gcf);

		result.reduce();

		return result;
	}

	public IrrationalFraction subtract(int num)
	{
		return this.subtract(new IrrationalFraction(num));
	}

	public IrrationalFraction multiply(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		result.numer = (this.numer * f.numer);
		result.denom = (this.denom * f.denom);

		result.reduce();

		return result;
	}

	public IrrationalFraction multiply(int scalar)
	{
		IrrationalFraction result = new IrrationalFraction((this.numer * scalar), this.denom);
		result.reduce();
		return result;
	}

	public IrrationalFraction divide(IrrationalFraction f)
	{
		IrrationalFraction result = new IrrationalFraction();

		if (f.numer == 0)
		{
			throw new ZeroDenomException(
					"Can't divide by a fraction with a '0' numerator.");
		}

		result.numer = (this.numer * f.denom);
		result.denom = (this.denom * f.numer);

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

		IrrationalFraction result = new IrrationalFraction(this.numer, (this.denom * scalar));
		result.reduce();
		return result;
	}

	public IrrationalFraction pow(int exponent)
	{
		IrrationalFraction result = new IrrationalFraction(this);

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

	public IrrationalFraction pow(IrrationalFraction exponent) throws NonIntFractionException
	{
		IrrationalFraction result = new IrrationalFraction(this);

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

	public int compareTo(IrrationalFraction frac)
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

	public int compare(int num)
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