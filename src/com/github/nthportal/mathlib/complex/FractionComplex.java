package com.github.nthportal.mathlib.complex;

import com.github.nthportal.mathlib.fraction.Fraction;
import com.github.nthportal.mathlib.util.SWWAIDKWException;

public class FractionComplex
{
    private static final int HASH_PRIME_1 = 17;
    private static final int HASH_PRIME_2 = 59;

    private Fraction real;
    private Fraction imaginary;

	public FractionComplex(Fraction realCoef, Fraction imaginaryCoef)
	{
		this.real = new Fraction(realCoef);
		this.imaginary = new Fraction(imaginaryCoef);
	}

	public FractionComplex(int realCoef, int imaginaryCoef)
	{
		this.real = new Fraction(realCoef);
		this.imaginary = new Fraction(imaginaryCoef);
	}

	public FractionComplex(Fraction imagNum)
	{
		this.real = new Fraction(0);
		this.imaginary = new Fraction(imagNum);
	}

	public FractionComplex(int imagNum)
	{
		this.real = new Fraction(0);
		this.imaginary = new Fraction(imagNum);
	}

	public FractionComplex(FractionComplex c)
	{
		this.real = new Fraction(c.real);
		this.imaginary = new Fraction(c.imaginary);
	}

    public Fraction getReal()
    {
        return new Fraction(real);
    }

    public Fraction getImaginary()
    {
        return new Fraction(imaginary);
    }

    public FractionComplex conjugate()
    {
        return new FractionComplex(this.real, (this.imaginary.multiply(-1)));
    }

	public FractionComplex add(FractionComplex c)
	{
		return new FractionComplex(this.real.add(c.real),
				this.imaginary.add(c.imaginary));
	}

	public FractionComplex subtract(FractionComplex c)
	{
		return new FractionComplex(this.real.subtract(c.real),
				this.imaginary.subtract(c.imaginary));
	}

	public FractionComplex multiply(int scalar)
	{
		return new FractionComplex(this.real.multiply(scalar),
				this.imaginary.multiply(scalar));
	}

	public FractionComplex multiply(Fraction frac)
	{
		return new FractionComplex(this.real.multiply(frac),
				this.imaginary.multiply(frac));
	}

	public FractionComplex multiply(FractionComplex c)
	{
		Fraction real = this.real.multiply(c.real).subtract(
				this.imaginary.multiply(c.imaginary));
		Fraction imaginary = this.real.multiply(c.imaginary).add(
				this.imaginary.multiply(c.real));
		return new FractionComplex(real, imaginary);
	}

	public FractionComplex divide(int scalar)
	{
		return new FractionComplex(this.real.divide(scalar),
				this.imaginary.divide(scalar));
	}

	public FractionComplex divide(Fraction frac)
	{
		return new FractionComplex(this.real.divide(frac),
				this.imaginary.divide(frac));
	}

	public FractionComplex divide(FractionComplex denom)
	{
		FractionComplex numer = new FractionComplex(this);

		FractionComplex conjugate = denom.conjugate();

		numer = numer.multiply(conjugate);
		denom = denom.multiply(conjugate);

		if (!(denom.imaginary.compareTo(0) == 0))
		{
			throw new SWWAIDKWException();
		}

		return new FractionComplex(numer.real.divide(denom.real),
				numer.imaginary.divide(denom.real));
	}

	public FractionComplex pow(int exp)
	{
		if (exp < 0)
		{
			throw new IllegalArgumentException(
					"Cannot raise complex number to a negative power.");
		}

		if (exp == 0)
		{
			return new FractionComplex(1, 0);
		}

		FractionComplex result = new FractionComplex(this);

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FractionComplex)) {
            return false;  // takes care of null check
        }

        FractionComplex that = (FractionComplex) obj;

        return (this.real.equals(that.real) && this.imaginary.equals(that.imaginary));
    }

    public int hashCode()
    {
        int result = HASH_PRIME_1;
        result = HASH_PRIME_2 * result + this.real.hashCode();
        result = HASH_PRIME_2 * result + this.imaginary.hashCode();
        return result;
    }

	public void print()
	{
		this.real.print();
		System.out.print(" + ");
		this.imaginary.print();
		System.out.print("i");
	}

	public void println()
	{
		this.real.print();
		System.out.print(" + ");
		this.imaginary.print();
		System.out.println("i");
	}

	public static void main(String[] args)
	{
		FractionComplex c1 = new FractionComplex(4, 2);
		FractionComplex c2 = new FractionComplex(3, -1);

		c1.println();
		c2.println();

		FractionComplex result = c1.divide(c2);

		result.println();
	}
}
