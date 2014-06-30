package net.nth.mathlib.fraction;

import net.nth.mathlib.util.Algebra;

public class Fraction
{
	private int numer;
	private int denom;

	public Fraction()
	{
	}

	public Fraction(int numer, int denom)
	{
		if (denom == 0)
		{
			throw new ZeroDenomException();
		}

		this.numer = numer;
		this.denom = denom;
		this.reduce();
	}

	public Fraction(int whole)
	{
		this.numer = whole;
		this.denom = 1;
	}

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

	public static Fraction add(Fraction f1, Fraction f2)
	{
		Fraction result = new Fraction();

		int gcf = Algebra.gcf(f1.denom, f2.denom);

		result.denom = (f1.denom * f2.denom / gcf);
		result.numer = (f1.numer * f2.denom / gcf)
				+ (f2.numer * f1.denom / gcf);

		result.reduce();

		return result;
	}
	
	public Fraction add(int num)
	{
		return add(this, new Fraction(num));
	}

	public static Fraction subtract(Fraction f1, Fraction f2)
	{
		Fraction result = new Fraction();

		int gcf = Algebra.gcf(f1.denom, f2.denom);

		result.denom = (f1.denom * f2.denom / gcf);
		result.numer = (f1.numer * f2.denom / gcf)
				- (f2.numer * f1.denom / gcf);

		result.reduce();

		return result;
	}

	public Fraction subtract(int num)
	{
		return subtract(this, new Fraction(num));
	}

	public static Fraction multiply(Fraction f1, Fraction f2)
	{
		Fraction result = new Fraction();

		result.numer = (f1.numer * f2.numer);
		result.denom = (f1.denom * f2.denom);

		result.reduce();

		return result;
	}
	
	public Fraction multiply(int scalar)
	{
		Fraction result = new Fraction((this.numer * scalar), this.denom);
		result.reduce();
		return result;
	}

	public static Fraction divide(Fraction f1, Fraction f2)
	{
		Fraction result = new Fraction();

		if (f2.numer == 0)
		{
			throw new ZeroDenomException(
					"Can't divide by a fraction with a '0' numerator.");
		}

		result.numer = (f1.numer * f2.denom);
		result.denom = (f1.denom * f2.numer);

		result.fixNegativeDenom();

		result.reduce();

		return result;
	}
	
	public Fraction divide(int scalar)
	{
		if (scalar == 0)
		{
			throw new ZeroDenomException("Can't divide by 0.");
		}

		Fraction result = new Fraction(this.numer, (this.denom * scalar));
		result.reduce();
		return result;
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

	public static boolean compare(Fraction f1, Fraction f2)
	{
		if ((f1.numer == f2.numer) && (f1.denom == f2.denom))
		{
			return true;
		}
		// Else
		return false;
	}

	public boolean compare(int num)
	{
		if ((this.denom == 1) && (this.numer == num))
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