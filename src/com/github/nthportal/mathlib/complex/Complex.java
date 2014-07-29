package com.github.nthportal.mathlib.complex;

import com.github.nthportal.mathlib.util.SWWAIDKWException;
import com.github.nthportal.mathlib.util.ZeroDivisionException;

public class Complex
{
	private double real;
	private double imaginary;

	public Complex(double realCoef, double imaginaryCoef)
	{
		this.real = realCoef;
		this.imaginary = imaginaryCoef;
	}

	public Complex(double imagNum)
	{
		this.real = 0;
		this.imaginary = imagNum;
	}

	public Complex(Complex c)
	{
		this.real = c.real;
		this.imaginary = c.imaginary;
	}
	
	public double getReal()
	{
		return this.real;
	}
	
	public double getImaginary()
	{
		return this.imaginary;
	}
	
	public Complex conjugate()
	{
		return new Complex(this.real, (this.imaginary * -1));
	}

	public Complex add(Complex c)
	{
		return new Complex((this.real + c.real), (this.imaginary + c.imaginary));
	}

	public Complex subtract(Complex c)
	{
		return new Complex((this.real - c.real), (this.imaginary - c.imaginary));
	}

	public Complex multiply(double scalar)
	{
		return new Complex((this.real * scalar), (this.imaginary * scalar));
	}

	public Complex multiply(Complex c)
	{
		double real = ((this.real * c.real) - (this.imaginary * c.imaginary));
		double imaginary = ((this.real * c.imaginary) + (this.imaginary * c.real));
		return new Complex(real, imaginary);
	}

	public Complex divide(double scalar)
	{
		if (scalar == 0)
		{
			throw new ZeroDivisionException();
		}

		return new Complex((this.real / scalar), (this.imaginary / scalar));
	}

	public Complex divide(Complex denom)
	{
		Complex numer = new Complex(this);

		Complex conjugate = denom.conjugate();

		numer = numer.multiply(conjugate);
		denom = denom.multiply(conjugate);

		if (!(denom.imaginary == 0))
		{
			throw new SWWAIDKWException();
		}

		if (numer.real == 0)
		{
			throw new ZeroDivisionException();
		}

		return new Complex((numer.real / denom.real),
				(numer.imaginary / denom.real));
	}

	public Complex pow(int exp)
	{
		if (exp < 0)
		{
			throw new IllegalArgumentException(
					"Cannot raise complex number to a negative power.");
		}

		if (exp == 0)
		{
			return new Complex(1, 0);
		}

		Complex result = new Complex(this);

		for (int i = 0; i < (exp - 1); i++)
		{
			result = result.multiply(this);
		}

		return result;
	}

	public boolean equals(Complex c)
	{
		if (this.real == c.real && this.imaginary == c.imaginary)
		{
			return true;
		}
		// Else
		return false;
	}

	public void print()
	{
		System.out.print(this.real + " + " + this.imaginary + "i");
	}

	public void println()
	{
		System.out.println(this.real + " + " + this.imaginary + "i");
	}
}
